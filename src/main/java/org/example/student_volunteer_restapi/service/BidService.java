package org.example.student_volunteer_restapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.student_volunteer_restapi.dto.bid.PatchBidDto;
import org.example.student_volunteer_restapi.dto.bid.RequestBidDto;
import org.example.student_volunteer_restapi.dto.bid.ResponseBidDto;
import org.example.student_volunteer_restapi.exception.BadRequestException;
import org.example.student_volunteer_restapi.exception.BadRequestListException;
import org.example.student_volunteer_restapi.exception.ConflictException;
import org.example.student_volunteer_restapi.exception.NotFoundException;
import org.example.student_volunteer_restapi.model.*;
import org.example.student_volunteer_restapi.model.mapper.BidMapper;
import org.example.student_volunteer_restapi.repository.BidRepository;
import org.example.student_volunteer_restapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final BidMapper bidMapper;
    private final ObjectMapper objectMapper;

    public ResponseBidDto getOne(Long id) {
        Optional<Bid> bidOptional = bidRepository.findById(id);
        return bidMapper.toResponseBidDto(bidOptional.orElseThrow(() ->
                new NotFoundException("Bid with id `%s` not found".formatted(id))));
    }

    public List<ResponseBidDto> findByUser_IdAndMarkedDeleteFalse(Long userId, boolean markedDeleteFalse) {
        List<Bid> bids = bidRepository.findByUser_IdAndMarkedDelete(userId, markedDeleteFalse);
        return bids.stream()
                .map(bidMapper::toResponseBidDto)
                .toList();
    }

    public ResponseBidDto create(RequestBidDto dto, User user) {
        Bid bid = bidMapper.toEntity(dto);
        bid.setUser(user);

        if (isInvalidScheduleInBid(bid.getSchedules())) {
            throw new BadRequestException("Schedules not valid");
        }

        Bid resultBid = bidRepository.save(bid);
        return bidMapper.toResponseBidDto(resultBid);
    }

    public void delete(Long id) {
        Optional<Bid> bidOptional = bidRepository.findById(id);
        if (bidOptional.isEmpty()) {
            throw new NotFoundException("Bid with id `%s` not found".formatted(id));
        }
        if (!bidOptional.get().isMarkedDelete()) {
            throw new ConflictException("Bid with id `%s` not marked for delete".formatted(id));
        }
        bidOptional.get().getUser().removeBid(bidOptional.get());
        System.out.println(id);
        bidRepository.delete(bidOptional.get());

    }

    public PatchBidDto patch(Long id, JsonNode patchNode) throws IOException {
        Bid bid = bidRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Bid with id `%s` not found".formatted(id)));

        PatchBidDto patchBidDto = bidMapper.toPatchBidDto(bid);
        objectMapper.readerForUpdating(patchBidDto).readValue(patchNode);
        bidMapper.updateWithNull(patchBidDto, bid);

        if (isInvalidScheduleInBid(bid.getSchedules())) {
            throw new BadRequestException("Schedules not valid");
        }

        Bid resultBid = bidRepository.save(bid);
        return bidMapper.toPatchBidDto(resultBid);
    }

    private boolean isInvalidScheduleInBid(List<Schedule> schedules) {
        if (schedules.isEmpty()) {
            throw new BadRequestException("Schedules is empty");
        }
        // проверка на правильность времени в каждом дне
        for (Schedule schedule : schedules) {
            if (schedule.getStartTime().isAfter(schedule.getEndTime())) {
                List<String> messages = new ArrayList<>();
                messages.add("Bid has invalid schedule: ");
                messages.add(String.format("start: '%s'", schedule.getStartTime()));
                messages.add(String.format("end: '%s'", schedule.getEndTime()));
                throw new BadRequestListException(messages);
            }
        }
        // получение дублирующихся дней недели
        Set<DayOfWeek> set = new HashSet<>();
        List<DayOfWeek> duplicateDays = new ArrayList<>();
        for (Schedule schedule : schedules) {
            if (!set.add(schedule.getDayOfWeek())) {
                duplicateDays.add(schedule.getDayOfWeek());
            }
        }
        // проходимся по каждому дубль дню
        for (DayOfWeek dayOfWeek : duplicateDays) {
            List<Schedule> duplicate = schedules.stream()
                    .filter(s -> s.getDayOfWeek().equals(dayOfWeek))
                    .sorted(Comparator.comparing(Schedule::getStartTime))
                    .toList();
            // проверяем, нет ли перекрестных периодов времени
            for (int i = 0; i < duplicate.size() - 1; i++) {
                if (duplicate.get(i).getEndTime().isAfter(duplicate.get(i + 1).getStartTime())) {
                    List<String> messages = new ArrayList<>();
                    messages.add("Bid has invalid schedule: two times overlap on the same day:");
                    messages.add(String.format("%s", dayOfWeek));
                    messages.add(duplicate.get(i).toString());
                    messages.add(duplicate.get(i + 1).toString());
                    throw new BadRequestListException(messages);
                }
            }
        }
        return false;
    }

    public void markBidAsDeleted(Long userId, Long id)  {
        Bid bid = bidRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format(
                        "Bid with id '%s' not found!", id)));
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format(
                    "User with id '%s' not found!", userId));
        }
        if (!bid.getUser().getId().equals(userId)) {
            throw new NotFoundException(String.format(
                    "Bid with id '%s' not found!", id));
        }
        if (bid.getStatusBid() != StatusBid.IN_PROGRESS) {
            throw new ConflictException(String.format(
                    "The request cannot be marked for delete because it has the status: '%s'", bid.getStatusBid()));
        }
        bid.setMarkedDelete(true);
        bidRepository.save(bid);
    }

    public List<ResponseBidDto> getAll() {
        List<Bid> bids = bidRepository.findAll();
        return bids.stream()
                .map(bidMapper::toResponseBidDto)
                .toList();
    }

    public List<Bid> findByUser_Id(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(String.format(
                    "User with id '%s' not found", id));
        }
        return bidRepository.findByUser_Id(id);
    }

}

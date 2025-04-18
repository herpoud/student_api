package org.example.student_volunteer_restapi.model.mapper;

import org.example.student_volunteer_restapi.dto.bid.PatchBidDto;
import org.example.student_volunteer_restapi.dto.bid.RequestBidDto;
import org.example.student_volunteer_restapi.dto.bid.ResponseBidDto;
import org.example.student_volunteer_restapi.model.Bid;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ScheduleMapper.class})
public interface BidMapper {
    @Mapping(source = "user.id", target = "userId")
    ResponseBidDto toResponseBidDto(Bid bid);

    @AfterMapping
    default void linkSchedules(@MappingTarget Bid bid) {
        bid.getSchedules().forEach(schedule -> schedule.setBid(bid));
    }

    Bid toEntity(RequestBidDto requestBidDto);

    RequestBidDto toRequestBidDto(Bid bid);

    Bid toEntity(PatchBidDto patchBidDto);

    PatchBidDto toPatchBidDto(Bid bid);

    Bid updateWithNull(PatchBidDto patchBidDto, @MappingTarget Bid bid);
}
package org.example.student_volunteer_restapi.model.mapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.student_volunteer_restapi.dto.bid.PatchBidDto;
import org.example.student_volunteer_restapi.dto.bid.RequestBidDto;
import org.example.student_volunteer_restapi.dto.bid.ResponseBidDto;
import org.example.student_volunteer_restapi.dto.schedule.RequestScheduleDto;
import org.example.student_volunteer_restapi.dto.schedule.ResponseScheduleDto;
import org.example.student_volunteer_restapi.model.Bid;
import org.example.student_volunteer_restapi.model.DayOfWeek;
import org.example.student_volunteer_restapi.model.Schedule;
import org.example.student_volunteer_restapi.model.StatusBid;
import org.example.student_volunteer_restapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T19:29:38+0500",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class BidMapperImpl implements BidMapper {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ResponseBidDto toResponseBidDto(Bid bid) {
        if ( bid == null ) {
            return null;
        }

        Long userId = null;
        List<ResponseScheduleDto> schedules = null;
        Long id = null;
        LocalDateTime creationDate = null;
        boolean markedDelete = false;
        StatusBid statusBid = null;

        userId = bidUserId( bid );
        schedules = scheduleListToResponseScheduleDtoList( bid.getSchedules() );
        id = bid.getId();
        creationDate = bid.getCreationDate();
        markedDelete = bid.isMarkedDelete();
        statusBid = bid.getStatusBid();

        ResponseBidDto responseBidDto = new ResponseBidDto( id, userId, creationDate, schedules, markedDelete, statusBid );

        return responseBidDto;
    }

    @Override
    public Bid toEntity(RequestBidDto requestBidDto) {
        if ( requestBidDto == null ) {
            return null;
        }

        Bid bid = new Bid();

        bid.setSchedules( requestScheduleDtoListToScheduleList( requestBidDto.getSchedules() ) );

        linkSchedules( bid );

        return bid;
    }

    @Override
    public RequestBidDto toRequestBidDto(Bid bid) {
        if ( bid == null ) {
            return null;
        }

        List<RequestScheduleDto> schedules = null;

        schedules = scheduleListToRequestScheduleDtoList( bid.getSchedules() );

        RequestBidDto requestBidDto = new RequestBidDto( schedules );

        return requestBidDto;
    }

    @Override
    public Bid toEntity(PatchBidDto patchBidDto) {
        if ( patchBidDto == null ) {
            return null;
        }

        Bid bid = new Bid();

        bid.setSchedules( requestScheduleDtoListToScheduleList( patchBidDto.getSchedules() ) );
        bid.setStatusBid( patchBidDto.getStatusBid() );

        linkSchedules( bid );

        return bid;
    }

    @Override
    public PatchBidDto toPatchBidDto(Bid bid) {
        if ( bid == null ) {
            return null;
        }

        List<RequestScheduleDto> schedules = null;
        StatusBid statusBid = null;

        schedules = scheduleListToRequestScheduleDtoList( bid.getSchedules() );
        statusBid = bid.getStatusBid();

        PatchBidDto patchBidDto = new PatchBidDto( schedules, statusBid );

        return patchBidDto;
    }

    @Override
    public Bid updateWithNull(PatchBidDto patchBidDto, Bid bid) {
        if ( patchBidDto == null ) {
            return bid;
        }

        if ( bid.getSchedules() != null ) {
            List<Schedule> list = requestScheduleDtoListToScheduleList( patchBidDto.getSchedules() );
            if ( list != null ) {
                bid.getSchedules().clear();
                bid.getSchedules().addAll( list );
            }
            else {
                bid.setSchedules( null );
            }
        }
        else {
            List<Schedule> list = requestScheduleDtoListToScheduleList( patchBidDto.getSchedules() );
            if ( list != null ) {
                bid.setSchedules( list );
            }
        }
        bid.setStatusBid( patchBidDto.getStatusBid() );

        linkSchedules( bid );

        return bid;
    }

    private Long bidUserId(Bid bid) {
        User user = bid.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    protected List<ResponseScheduleDto> scheduleListToResponseScheduleDtoList(List<Schedule> list) {
        if ( list == null ) {
            return null;
        }

        List<ResponseScheduleDto> list1 = new ArrayList<ResponseScheduleDto>( list.size() );
        for ( Schedule schedule : list ) {
            list1.add( scheduleMapper.toResponseScheduleDto( schedule ) );
        }

        return list1;
    }

    protected Schedule requestScheduleDtoToSchedule(RequestScheduleDto requestScheduleDto) {
        if ( requestScheduleDto == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setId( requestScheduleDto.getId() );
        schedule.setDayOfWeek( requestScheduleDto.getDayOfWeek() );
        schedule.setStartTime( requestScheduleDto.getStartTime() );
        schedule.setEndTime( requestScheduleDto.getEndTime() );

        return schedule;
    }

    protected List<Schedule> requestScheduleDtoListToScheduleList(List<RequestScheduleDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Schedule> list1 = new ArrayList<Schedule>( list.size() );
        for ( RequestScheduleDto requestScheduleDto : list ) {
            list1.add( requestScheduleDtoToSchedule( requestScheduleDto ) );
        }

        return list1;
    }

    protected RequestScheduleDto scheduleToRequestScheduleDto(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        Long id = null;
        DayOfWeek dayOfWeek = null;
        LocalTime startTime = null;
        LocalTime endTime = null;

        id = schedule.getId();
        dayOfWeek = schedule.getDayOfWeek();
        startTime = schedule.getStartTime();
        endTime = schedule.getEndTime();

        RequestScheduleDto requestScheduleDto = new RequestScheduleDto( id, dayOfWeek, startTime, endTime );

        return requestScheduleDto;
    }

    protected List<RequestScheduleDto> scheduleListToRequestScheduleDtoList(List<Schedule> list) {
        if ( list == null ) {
            return null;
        }

        List<RequestScheduleDto> list1 = new ArrayList<RequestScheduleDto>( list.size() );
        for ( Schedule schedule : list ) {
            list1.add( scheduleToRequestScheduleDto( schedule ) );
        }

        return list1;
    }
}

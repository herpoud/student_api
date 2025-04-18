package org.example.student_volunteer_restapi.model.mapper;

import java.time.LocalTime;
import javax.annotation.processing.Generated;
import org.example.student_volunteer_restapi.dto.schedule.ResponseScheduleDto;
import org.example.student_volunteer_restapi.model.DayOfWeek;
import org.example.student_volunteer_restapi.model.Schedule;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T19:29:38+0500",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class ScheduleMapperImpl implements ScheduleMapper {

    @Override
    public Schedule toEntity(ResponseScheduleDto responseScheduleDto) {
        if ( responseScheduleDto == null ) {
            return null;
        }

        Schedule schedule = new Schedule();

        schedule.setDayOfWeek( responseScheduleDto.getDayOfWeek() );
        schedule.setStartTime( responseScheduleDto.getStartTime() );
        schedule.setEndTime( responseScheduleDto.getEndTime() );

        return schedule;
    }

    @Override
    public ResponseScheduleDto toResponseScheduleDto(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        DayOfWeek dayOfWeek = null;
        LocalTime startTime = null;
        LocalTime endTime = null;

        dayOfWeek = schedule.getDayOfWeek();
        startTime = schedule.getStartTime();
        endTime = schedule.getEndTime();

        ResponseScheduleDto responseScheduleDto = new ResponseScheduleDto( dayOfWeek, startTime, endTime );

        return responseScheduleDto;
    }
}

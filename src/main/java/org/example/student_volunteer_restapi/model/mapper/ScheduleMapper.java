package org.example.student_volunteer_restapi.model.mapper;

import org.example.student_volunteer_restapi.dto.schedule.ResponseScheduleDto;
import org.example.student_volunteer_restapi.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ScheduleMapper {
    Schedule toEntity(ResponseScheduleDto responseScheduleDto);

    ResponseScheduleDto toResponseScheduleDto(Schedule schedule);
}
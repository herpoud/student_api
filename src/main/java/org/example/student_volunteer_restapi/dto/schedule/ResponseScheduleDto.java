package org.example.student_volunteer_restapi.dto.schedule;

import lombok.Value;
import org.example.student_volunteer_restapi.model.DayOfWeek;

import java.time.LocalTime;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.Schedule}
 */
@Value
public class ResponseScheduleDto {
    DayOfWeek dayOfWeek;
    LocalTime startTime;
    LocalTime endTime;
}

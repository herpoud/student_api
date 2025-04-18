package org.example.student_volunteer_restapi.dto.schedule;

import lombok.NonNull;
import lombok.Value;
import org.example.student_volunteer_restapi.model.DayOfWeek;

import java.time.LocalTime;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.Schedule}
 */
@Value
public class RequestScheduleDto {

    Long id;
    @NonNull
    DayOfWeek dayOfWeek;
    @NonNull
    LocalTime startTime;
    @NonNull
    LocalTime endTime;
}

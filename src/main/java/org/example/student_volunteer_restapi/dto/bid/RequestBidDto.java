package org.example.student_volunteer_restapi.dto.bid;

import lombok.NonNull;
import lombok.Value;
import org.example.student_volunteer_restapi.dto.schedule.RequestScheduleDto;

import java.util.List;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.Bid}
 */
@Value
public class RequestBidDto {

    @NonNull
    List<RequestScheduleDto> schedules;

}
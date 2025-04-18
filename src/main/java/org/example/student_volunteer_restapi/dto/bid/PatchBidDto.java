package org.example.student_volunteer_restapi.dto.bid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.example.student_volunteer_restapi.dto.schedule.RequestScheduleDto;
import org.example.student_volunteer_restapi.model.Schedule;
import org.example.student_volunteer_restapi.model.StatusBid;

import java.util.List;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.Bid}
 */
@Value
public class PatchBidDto {
    @NonNull
    List<RequestScheduleDto> schedules;
    StatusBid statusBid;
}
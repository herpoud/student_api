package org.example.student_volunteer_restapi.dto.bid;

import lombok.Value;
import org.example.student_volunteer_restapi.dto.schedule.ResponseScheduleDto;
import org.example.student_volunteer_restapi.model.StatusBid;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.Bid}
 */
@Value
public class ResponseBidDto {
    Long id;
    Long userId;
    LocalDateTime creationDate;
    List<ResponseScheduleDto> schedules;
    boolean markedDelete;
    StatusBid statusBid;


}
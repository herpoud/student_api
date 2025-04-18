package org.example.student_volunteer_restapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.student_volunteer_restapi.dto.bid.RequestBidDto;
import org.example.student_volunteer_restapi.dto.bid.ResponseBidDto;
import org.example.student_volunteer_restapi.dto.user.ResponseUserDto;
import org.example.student_volunteer_restapi.model.User;
import org.example.student_volunteer_restapi.model.mapper.UserMapper;
import org.example.student_volunteer_restapi.service.BidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final BidService bidService;
    private final UserMapper userMapper;

    @GetMapping("/bids")
    public ResponseEntity<List<ResponseBidDto>> findByUser_Id() {
        User user = getUserFromContext();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bidService.findByUser_IdAndMarkedDeleteFalse(user.getId(), false));
    }

    @GetMapping
    public ResponseEntity<ResponseUserDto> getUser() {
        User user = getUserFromContext();
        return ResponseEntity.ok(userMapper.toResponseUserDto(user));
    }

    @PostMapping("/bids")
    public ResponseEntity<ResponseBidDto> create(@RequestBody RequestBidDto dto) {
        User user = getUserFromContext();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bidService.create(dto, user));
    }

    @DeleteMapping("/bids/{bidId}")
    public ResponseEntity<Void> markBidAsDeleted(@PathVariable Long bidId) {
        User user = getUserFromContext();
        bidService.markBidAsDeleted(user.getId(), bidId);
        return ResponseEntity.noContent().build();
    }

    private User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

}

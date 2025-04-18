package org.example.student_volunteer_restapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.example.student_volunteer_restapi.dto.bid.PatchBidDto;
import org.example.student_volunteer_restapi.dto.bid.ResponseBidDto;
import org.example.student_volunteer_restapi.dto.user.PatchUserDto;
import org.example.student_volunteer_restapi.dto.user.ResponseUserDto;
import org.example.student_volunteer_restapi.model.Bid;
import org.example.student_volunteer_restapi.model.User;
import org.example.student_volunteer_restapi.model.mapper.BidMapper;
import org.example.student_volunteer_restapi.service.BidService;
import org.example.student_volunteer_restapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BidService bidService;
    private final BidMapper bidMapper;

    @GetMapping("/users")
    public List<ResponseUserDto> findUsers() {
        return userService.getAll();
    }

    @GetMapping("/bids")
    public ResponseEntity<List<ResponseBidDto>> findBids() {
        return ResponseEntity.ok(bidService.getAll());
    }

    @GetMapping("/bids/{bidId}")
    public ResponseEntity<ResponseBidDto> findBid(@PathVariable Long bidId) {
        return ResponseEntity.ok(bidService.getOne(bidId));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseUserDto> findUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getOne(id));
    }

    @GetMapping("/users/{id}/bids")
    public ResponseEntity<List<ResponseBidDto>> findUserBids(@PathVariable Long id) {
        List<Bid> bids = bidService.findByUser_Id(id);
        List<ResponseBidDto> bidsDto = bids.stream().map(bidMapper::toResponseBidDto).toList();
        return ResponseEntity.ok(bidsDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = getUserFromContext();
        userService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<PatchUserDto> patchUser(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        User user = getUserFromContext();
        return ResponseEntity.ok(userService.patch(id, patchNode, user.getId()));
    }

    @PatchMapping("/bids/{id}")
    public ResponseEntity<PatchBidDto> patchBid(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        return ResponseEntity.ok(bidService.patch(id, patchNode));
    }

    @DeleteMapping("/bids/{id}")
    public ResponseEntity<?> deleteBid(@PathVariable Long id) {
        bidService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

}

package org.example.student_volunteer_restapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.student_volunteer_restapi.dto.token.ResponseTokenDto;
import org.example.student_volunteer_restapi.dto.user.LoginUserDto;
import org.example.student_volunteer_restapi.dto.user.RequestUserDto;
import org.example.student_volunteer_restapi.dto.user.ResponseUserDto;
import org.example.student_volunteer_restapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> create(@RequestBody RequestUserDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDto> login(@RequestBody LoginUserDto loginUserDto)  {
        return ResponseEntity.ok(userService.loginUser(loginUserDto));
    }

}

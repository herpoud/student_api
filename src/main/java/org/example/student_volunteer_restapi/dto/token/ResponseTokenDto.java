package org.example.student_volunteer_restapi.dto.token;

import lombok.Value;

@Value
public class ResponseTokenDto {

    String token;

    public ResponseTokenDto(String token) {
        this.token = token;
    }
}

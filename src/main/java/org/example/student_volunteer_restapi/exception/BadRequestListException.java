package org.example.student_volunteer_restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestListException extends RuntimeException {

    public List<String> messages = new ArrayList<>();

    public BadRequestListException(String message) {
        super(message);
    }

    public BadRequestListException(List<String> messages) {
        super(String.join(" ", messages));
        this.messages = messages;

    }

}

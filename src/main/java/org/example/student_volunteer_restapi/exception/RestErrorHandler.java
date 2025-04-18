package org.example.student_volunteer_restapi.exception;

import org.example.student_volunteer_restapi.messages.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        return getResponseEntityForCustomExceptions(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return getResponseEntityForCustomExceptions(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BadRequestListException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestListException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        request.getDescription(false),
                        ex.messages
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return getResponseEntityForCustomExceptions(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
        return getResponseEntityForCustomExceptions(ex, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return getResponseEntityForCustomExceptions(ex, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return getResponseEntityForCustomExceptions(ex, HttpStatus.NOT_FOUND, request);
    }

    private ResponseEntity<Object> getResponseEntityForCustomExceptions(Exception ex, HttpStatus status, WebRequest request) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());
        return ResponseEntity
                .status(status)
                .body(new ExceptionMessage(
                        status.value(),
                        request.getDescription(false),
                        messages
                ));
    }


}
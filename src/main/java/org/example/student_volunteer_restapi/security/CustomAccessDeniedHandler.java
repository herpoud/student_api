package org.example.student_volunteer_restapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.student_volunteer_restapi.messages.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        List<String> messages = new ArrayList<>();
        messages.add("Нет прав доступа!");

        String errorResponse = objectMapper.writeValueAsString(new ExceptionMessage(
                HttpStatus.FORBIDDEN.value(), request.getRequestURI(), messages
        ));

        response.getWriter().write(errorResponse);

    }
}

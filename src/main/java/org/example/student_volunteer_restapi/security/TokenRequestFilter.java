package org.example.student_volunteer_restapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.student_volunteer_restapi.model.User;
import org.example.student_volunteer_restapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class TokenRequestFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public TokenRequestFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String token = authHeader.replace("Bearer ", "");
            Optional<User> userOptional = userRepository.findByToken(token);
            if (userOptional.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            CustomAuthentication authentication = new CustomAuthentication(userOptional.get());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}

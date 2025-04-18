package org.example.student_volunteer_restapi.security.utils;

import lombok.RequiredArgsConstructor;
import org.example.student_volunteer_restapi.model.User;
import org.example.student_volunteer_restapi.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class TokenUtils {

    private static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private static final int SIZE = 64;

    public String generateToken() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(SIZE);
        for (int i = 0; i < SIZE; i++) {
            int ind = random.nextInt(0, SYMBOLS.length() - 1);
            sb.append(SYMBOLS.charAt(ind));
        }
        return sb.toString();
    }


}

package org.example.student_volunteer_restapi.dto.user;

import lombok.NonNull;
import lombok.Value;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.User}
 */
@Value
public class RequestUserDto {
    @NonNull
    String email;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    String patronymicName;
    @NonNull
    String password;
    @NonNull
    String confirmPassword;
}
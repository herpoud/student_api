package org.example.student_volunteer_restapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.User}
 */
@Value
public class LoginUserDto {
    @NonNull
    String email;
    @NonNull
    String password;
}
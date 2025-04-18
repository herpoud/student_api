package org.example.student_volunteer_restapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * DTO for {@link org.example.student_volunteer_restapi.model.User}
 */
@Value
public class PatchUserDto {
    String firstName;
    String lastName;
    String patronymicName;
}
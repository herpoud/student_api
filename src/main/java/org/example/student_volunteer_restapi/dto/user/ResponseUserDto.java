package org.example.student_volunteer_restapi.dto.user;

import lombok.Value;
import org.example.student_volunteer_restapi.model.Bid;
import org.example.student_volunteer_restapi.model.Role;

import java.util.List;
import java.util.Set;

@Value
public class ResponseUserDto {

    String id;
    String email;
    String firstName;
    String lastName;
    String patronymicName;

    Set<Role> roles;

}

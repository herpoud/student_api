package org.example.student_volunteer_restapi.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionMessage {

    int code;
    String endpoint;
    List<String> messages;

}

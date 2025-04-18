package org.example.student_volunteer_restapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id;

    @Column(unique = true, name = "name")
    String name;

}

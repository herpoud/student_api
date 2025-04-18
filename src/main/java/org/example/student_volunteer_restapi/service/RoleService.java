package org.example.student_volunteer_restapi.service;

import lombok.RequiredArgsConstructor;
import org.example.student_volunteer_restapi.exception.NotFoundException;
import org.example.student_volunteer_restapi.model.Role;
import org.example.student_volunteer_restapi.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole()  {
        Role role = roleRepository.findByName("USER").orElse(null);
        if (role == null) {
            throw new NotFoundException("Роль 'USER' невозможно добавить, т.к. её не существует");
        }
        return role;
    }

    public Role getAdminRole()  {
        Role role = roleRepository.findByName("ADMIN").orElse(null);
        if (role == null) {
            throw new NotFoundException("Роль 'ADMIN' невозможно добавить, т.к. её не существует");
        }
        return role;
    }

}

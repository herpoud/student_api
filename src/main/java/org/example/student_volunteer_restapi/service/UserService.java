package org.example.student_volunteer_restapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.student_volunteer_restapi.dto.token.ResponseTokenDto;
import org.example.student_volunteer_restapi.dto.user.LoginUserDto;
import org.example.student_volunteer_restapi.dto.user.PatchUserDto;
import org.example.student_volunteer_restapi.dto.user.RequestUserDto;
import org.example.student_volunteer_restapi.dto.user.ResponseUserDto;
import org.example.student_volunteer_restapi.exception.ConflictException;
import org.example.student_volunteer_restapi.exception.NotFoundException;
import org.example.student_volunteer_restapi.model.User;
import org.example.student_volunteer_restapi.model.mapper.UserMapper;
import org.example.student_volunteer_restapi.repository.UserRepository;
import org.example.student_volunteer_restapi.security.utils.TokenUtils;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    public void delete(Long id, Long actualId) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        if (user.get().getRoles().contains(roleService.getAdminRole())) {
            if (!user.get().getId().equals(actualId)) {
                throw new ConflictException("User has admin role");
            }
        }
        userRepository.delete(user.get());
    }

    public PatchUserDto patch(Long id, JsonNode patchNode, Long actualId) throws IOException {
        User user = findUserAndNoAdmin(id, actualId);
        PatchUserDto patchUserDto = userMapper.toPatchUserDto(user);
        objectMapper.readerForUpdating(patchUserDto).readValue(patchNode);
        userMapper.updateWithNull(patchUserDto, user);

        User resultUser = userRepository.save(user);
        return userMapper.toPatchUserDto(resultUser);
    }

    public ResponseTokenDto loginUser(LoginUserDto loginUserDto)  {
        String email = loginUserDto.getEmail();
        String password = loginUserDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CredentialsExpiredException("Неверный логин или пароль!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CredentialsExpiredException("Неверный логин или пароль!");
        }

        user.setToken(tokenUtils.generateToken());
        userRepository.save(user);

        return new ResponseTokenDto(user.getToken());
    }

    private User findUserAndNoAdmin(Long id, Long actualId) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id `%s` not found".formatted(id)));
        boolean admin = user.getRoles().contains(roleService.getAdminRole());
        if (admin && !user.getId().equals(actualId)) {
            throw new ConflictException("User has admin role!");
        }

        return user;
    }

    public ResponseUserDto create(RequestUserDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ConflictException("Пароли не совпадают!");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Пользователь с таким email уже существует");
        }
        User user = userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(Set.of(roleService.getUserRole()));

        User resultUser = userRepository.save(user);
        return userMapper.toResponseUserDto(resultUser);
    }

    public List<ResponseUserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseUserDto)
                .toList();
    }

    public ResponseUserDto getOne(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userMapper.toResponseUserDto(userOptional.orElseThrow(() ->
                new NotFoundException("User with id `%s` not found".formatted(id))));
    }

}

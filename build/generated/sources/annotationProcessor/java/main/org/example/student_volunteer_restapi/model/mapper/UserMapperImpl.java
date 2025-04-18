package org.example.student_volunteer_restapi.model.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.example.student_volunteer_restapi.dto.user.LoginUserDto;
import org.example.student_volunteer_restapi.dto.user.PatchUserDto;
import org.example.student_volunteer_restapi.dto.user.RequestUserDto;
import org.example.student_volunteer_restapi.dto.user.ResponseUserDto;
import org.example.student_volunteer_restapi.model.Role;
import org.example.student_volunteer_restapi.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-17T19:29:38+0500",
    comments = "version: 1.6.0, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RequestUserDto requestUserDto) {
        if ( requestUserDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( requestUserDto.getEmail() );
        user.setFirstName( requestUserDto.getFirstName() );
        user.setLastName( requestUserDto.getLastName() );
        user.setPatronymicName( requestUserDto.getPatronymicName() );
        user.setPassword( requestUserDto.getPassword() );

        return user;
    }

    @Override
    public RequestUserDto toRequestUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        String email = null;
        String firstName = null;
        String lastName = null;
        String patronymicName = null;
        String password = null;

        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        patronymicName = user.getPatronymicName();
        password = user.getPassword();

        String confirmPassword = null;

        RequestUserDto requestUserDto = new RequestUserDto( email, firstName, lastName, patronymicName, password, confirmPassword );

        return requestUserDto;
    }

    @Override
    public User toEntity(LoginUserDto loginUserDto) {
        if ( loginUserDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( loginUserDto.getEmail() );
        user.setPassword( loginUserDto.getPassword() );

        return user;
    }

    @Override
    public LoginUserDto toLoginUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        String email = null;
        String password = null;

        email = user.getEmail();
        password = user.getPassword();

        LoginUserDto loginUserDto = new LoginUserDto( email, password );

        return loginUserDto;
    }

    @Override
    public ResponseUserDto toResponseUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        Set<Role> roles = null;
        String id = null;
        String email = null;
        String firstName = null;
        String lastName = null;
        String patronymicName = null;

        Set<Role> set = user.getRoles();
        if ( set != null ) {
            roles = new LinkedHashSet<Role>( set );
        }
        if ( user.getId() != null ) {
            id = String.valueOf( user.getId() );
        }
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        patronymicName = user.getPatronymicName();

        ResponseUserDto responseUserDto = new ResponseUserDto( id, email, firstName, lastName, patronymicName, roles );

        return responseUserDto;
    }

    @Override
    public PatchUserDto toPatchUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String patronymicName = null;

        firstName = user.getFirstName();
        lastName = user.getLastName();
        patronymicName = user.getPatronymicName();

        PatchUserDto patchUserDto = new PatchUserDto( firstName, lastName, patronymicName );

        return patchUserDto;
    }

    @Override
    public User updateWithNull(PatchUserDto patchUserDto, User user) {
        if ( patchUserDto == null ) {
            return user;
        }

        user.setFirstName( patchUserDto.getFirstName() );
        user.setLastName( patchUserDto.getLastName() );
        user.setPatronymicName( patchUserDto.getPatronymicName() );

        return user;
    }
}

package org.example.student_volunteer_restapi.model.mapper;

import org.example.student_volunteer_restapi.dto.user.LoginUserDto;
import org.example.student_volunteer_restapi.dto.user.PatchUserDto;
import org.example.student_volunteer_restapi.dto.user.RequestUserDto;
import org.example.student_volunteer_restapi.dto.user.ResponseUserDto;
import org.example.student_volunteer_restapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(RequestUserDto requestUserDto);

    RequestUserDto toRequestUserDto(User user);

    User toEntity(LoginUserDto loginUserDto);

    LoginUserDto toLoginUserDto(User user);

    ResponseUserDto toResponseUserDto(User user);

    PatchUserDto toPatchUserDto(User user);

    User updateWithNull(PatchUserDto patchUserDto, @MappingTarget User user);
}
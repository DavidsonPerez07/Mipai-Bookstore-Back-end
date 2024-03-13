package com.udea.edyl.EDyL.web.mapper;

import java.util.List;

import org.mapstruct.factory.Mappers;

import com.udea.edyl.EDyL.data.entity.User;
import com.udea.edyl.EDyL.web.dto.UserDto;

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    List<User> userDtosTousers(List <UserDto> userDtos);
    
    List<UserDto> usersToUserDtos(List<User> users);
}

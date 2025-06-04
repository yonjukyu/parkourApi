package com.globalopencampus.parkourapi.dto.mapper;

import com.globalopencampus.parkourapi.dto.model.UserDto;
import com.globalopencampus.parkourapi.models.User;

public class UserMapper {
    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setPassword(userDto.password());
        user.setRoles(userDto.roles());
        return user;
    }
}

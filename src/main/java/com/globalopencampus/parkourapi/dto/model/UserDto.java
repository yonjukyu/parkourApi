package com.globalopencampus.parkourapi.dto.model;

import java.util.Set;

public record UserDto(
    String username,
    String email,
    String firstName,
    String lastName,
    String password,
    Set<String> roles
){}

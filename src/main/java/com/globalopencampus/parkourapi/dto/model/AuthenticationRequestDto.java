package com.globalopencampus.parkourapi.dto.model;

public record AuthenticationRequestDto(
    String username,
    String password
){}
package com.globalopencampus.parkourapi.controllers;

import com.globalopencampus.parkourapi.security.JwtUtil;
import com.globalopencampus.parkourapi.security.MyUserDetailsService;
import com.globalopencampus.parkourapi.dto.mapper.UserMapper;
import com.globalopencampus.parkourapi.dto.model.AuthenticationRequestDto;
import com.globalopencampus.parkourapi.dto.model.UserDto;
import com.globalopencampus.parkourapi.models.User;
import com.globalopencampus.parkourapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final MyUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(MyUserDetailsService userDetailsService, PasswordEncoder passwordEncoder, UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserDto userDto) {
        this.userService.getByUsername(userDto.username())
                .ifPresent((user) -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                });

        User user = UserMapper.toUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.post(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while creating the user"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDto.username(),
                            authenticationRequestDto.password()
                    )
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequestDto.username());
        List<String> userRoles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        final String jwt = this.jwtUtil.generateToken(userDetails.getUsername(), userRoles);

        return ResponseEntity.ok(Map.of("token", jwt));
    }
}

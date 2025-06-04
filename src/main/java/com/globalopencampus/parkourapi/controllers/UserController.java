package com.globalopencampus.parkourapi.controllers;

import com.globalopencampus.parkourapi.dto.mapper.UserMapper;
import com.globalopencampus.parkourapi.dto.model.UserDto;
import com.globalopencampus.parkourapi.models.User;
import com.globalopencampus.parkourapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService _userService;

    UserController(UserService userService) {
        _userService = userService;
    }

    @GetMapping("/{username}")
    public User getUsers(@PathVariable String username) {
        return _userService.getByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable long id) {
        return _userService.get(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_MANAGER')")
    public List<User> getUsers() {
        return _userService.getAll();
    }

    @PostMapping()
    public User createUser(@RequestBody UserDto userDto) {
        this._userService.getByUsername(userDto.username())
            .ifPresent((user) -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            });

        User user = UserMapper.toUser(userDto);
        return _userService.post(user)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while creating the user"));
    }

    @PutMapping("/{id}")
    @PostAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMINISTRATEUR')")
    public User updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        User existingUser = _userService.get(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        User user = UserMapper.toUser(userDto);
        user.setId(existingUser.getId());
        return _userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATEUR')")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        _userService.get(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        _userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

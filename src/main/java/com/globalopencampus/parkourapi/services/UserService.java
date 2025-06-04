package com.globalopencampus.parkourapi.services;

import com.globalopencampus.parkourapi.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> get(long id);
    /**
     * Get user by username
     * @param username the username
     * @return the user found with the given username
     */
    Optional<User> getByUsername(String username);
    List<User> getAll();

    Optional<User> post(User user);
    User update(long id, User user);
    void delete(long id);
}

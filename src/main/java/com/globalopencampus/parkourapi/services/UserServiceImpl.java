package com.globalopencampus.parkourapi.services;

import com.globalopencampus.parkourapi.models.User;
import com.globalopencampus.parkourapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository _userRepository;

    UserServiceImpl(UserRepository userRepository) {
        _userRepository = userRepository;
    }
    @Override
    public Optional<User> get(long id) {
        return _userRepository.findById(id);
    }
    @Override
    public Optional<User> getByUsername(String username) {
        return _userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAll() {
        return _userRepository.findAll();
    }

    @Override
    public Optional<User> post(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return Optional.of(_userRepository.save(user));
    }

    @Override
    public User update(long id, User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return _userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        _userRepository.deleteById(id);
    }
}

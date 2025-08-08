package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user, Long roleId);

    User getUser(Long id);

    void deleteUser(Long id);

    void updateUser(Long id, User user);

    Optional<User> findByUsername(String username);
}


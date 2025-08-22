package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    User saveUser(User user, Set<Long> roleIds);

    User getUser(Long id);

    void deleteUser(Long id);

    User updateUser(Long id, User user);

    Optional<User> findByUsername(String username);
}


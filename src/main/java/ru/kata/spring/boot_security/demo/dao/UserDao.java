package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAllUsers();

    User saveUser(User user);

    User getUser(Long id);

    void deleteUser(User user);

    User updateUser(User user);

    Optional<User> findByUsername(String username);

}
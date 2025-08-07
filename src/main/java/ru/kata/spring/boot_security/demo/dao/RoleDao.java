package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleDao {
    Set<Role> getAllRoles();

    Optional<Role> findByName(String name);

    void save(Role role);
}
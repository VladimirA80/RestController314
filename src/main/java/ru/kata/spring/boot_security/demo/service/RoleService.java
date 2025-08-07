package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {

    public Role createRole(String name) ;

    public Set<Role> getAllRoles();

}

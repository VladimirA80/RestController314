package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;


    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role createRole(String name) {
        Role role = new Role(name);
        roleDao.save(role);
        return role;
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

}

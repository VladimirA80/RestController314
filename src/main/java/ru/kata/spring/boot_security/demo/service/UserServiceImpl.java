package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void saveUser(User user, Set<Long> roleIds) {
        // Находим роль USER в базе
        Set<Role> managedRoles = roleIds.stream()
                .map(roleId -> roleDao.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId))
                )
                .collect(Collectors.toSet());
        // Устанавливаем роль и кодируем пароль
        user.setRoles(managedRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return Optional.ofNullable(userDao.getUser(id))
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userDao.getUser(id);
        userDao.deleteUser(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        User existingUser = userDao.getUser(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        Set<Role> managedRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role managedRole = roleDao.findById(role.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));
            managedRoles.add(managedRole);
        }
        existingUser.setRoles(managedRoles);
        userDao.updateUser(existingUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}



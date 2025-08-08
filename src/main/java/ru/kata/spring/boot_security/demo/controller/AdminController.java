package ru.kata.spring.boot_security.demo.controller;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getUsersList(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") User user, @RequestParam("roleId") Long roleId) {
        userService.saveUser(user, roleId);
        return "redirect:/admin"; // Перенаправление на список пользователей
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        try {
            userService.deleteUser(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("User not found " + e);
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam("id") Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") Long id, @ModelAttribute("user") User user, @RequestParam("roleId") Long roleId) {
        Role selectedRole = roleService.getRoleById(roleId);
        Set<Role> roles = new HashSet<>();
        roles.add(selectedRole);
        user.setRoles(roles);
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

}

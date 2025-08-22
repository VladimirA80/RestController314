package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final RoleService roleService;
    private final UserService userService;

    public AdminViewController(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    // Метод для отображения формы создания пользователя
    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        // Добавляем пустого пользователя для формы
        model.addAttribute("user", new User());

        // Добавляем список ролей для выбора в форме
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("allRoles", roles);

        return "new"; // Возвращает имя шаблона new.html
    }

    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "edit"; // Возвращает имя шаблона edit.html
    }
}


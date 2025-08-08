package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    private boolean enabled = true;

    //@ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Обязательные методы UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) {
            return Collections.emptyList();
        }

        return roles.stream()
                .filter(Objects::nonNull)
                .map(Role::getName)
                .filter(name -> name != null && !name.isEmpty())
                // Убедимся, что имя роли всегда с префиксом ROLE_
                .map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name)
                .distinct() // На случай дубликатов
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return enabled; }

    // Конструкторы
    public User() {}

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}

package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        List<Role> resultList = entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
        return new HashSet<>(resultList); // Преобразуем List в Set
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String name) {
        try {
            Role role = entityManager.createQuery(
                            "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(role);
        } catch (NoResultException e) {
            return Optional.empty(); // Важно! Возвращаем Optional.empty(), а не null
        }
    }

    @Override
    @Transactional
    public void save(Role role) {
        if (role.getId() == null) {
            entityManager.persist(role);
        } else {
            entityManager.merge(role);
        }
    }
}


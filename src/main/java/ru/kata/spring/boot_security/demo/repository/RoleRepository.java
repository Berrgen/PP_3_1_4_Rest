package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r WHERE r.name = ?1")
    Role findByName (String name);
}

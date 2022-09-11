package ru.kata.spring.boot_security.demo.models;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;

    @Column(name = "role_name", nullable = false, length = 45)
    private String name;

    public Role (Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role (Long id) {
        this.id = id;
    }

    public Role (String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @Override
    public String toString() {
        return this.name;
    }
}

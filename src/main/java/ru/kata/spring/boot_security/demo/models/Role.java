package ru.kata.spring.boot_security.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;

    @Column(name = "role_name", nullable = false, length = 45)
    private String name;

    public Role () {
    }

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

    @Override
    public String toString() {
        return this.name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    void saveUserWithDefaultRole(User user);
    List<User> getAllUsers();
    void addUser(User user);
    void deleteUserById(long id);
    void editUser(User user);
    User getUserById(long id);

}

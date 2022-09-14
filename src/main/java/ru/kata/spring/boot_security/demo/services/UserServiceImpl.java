package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        User userUpdated = userRepo.getById(user.getId());
        userUpdated.setFirstName(user.getFirstName());
        userUpdated.setLastName(user.getLastName());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setAge(user.getAge());
        userUpdated.setPassword(user.getPassword());
        userUpdated.setRoles(user.getRoles());
        userRepo.flush();
    }

    @Override
    public User getUserById(long id) {
        return userRepo.getById(id);
    }

    public User getByUserName(String email) {
        return userRepo.findByEmail(email);
    }

}

package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo,
                           RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public void saveUserWithDefaultRole(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        Role roleUser = roleRepo.findByName("User");
        user.addRole(roleUser);

        userRepo.save(user);
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
        userUpdated.setPassword(user.getPassword());
        userUpdated.setRoles(user.getRoles());
        userRepo.flush();
    }

    @Override
    public User getUserById(long id) {
        return userRepo.getById(id);
    }

}

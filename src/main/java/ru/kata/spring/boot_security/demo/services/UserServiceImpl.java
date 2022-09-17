package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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

    public User updateUserPutMap(User user) {
        User userDb = null;
        if (user.getRoles().size() == 0 || user.getPassword().equals("")) {
            userDb = getUserById(user.getId());
        }
        if(user.getRoles().size() != 0) {
            Set<Role> rolesSet = new HashSet<>();
            for (Role r : user.getRoles()) {
                rolesSet.add(roleService.getRole(r.getName()));
            }
            user.setRoles(rolesSet);
        } else {
            user.setRoles(userDb.getRoles());
        }
        if (user.getPassword().equals("")) {
            user.setPassword(userDb.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        editUser(user);
        return user;
    }


    public User addUserPostMap (User user) {
        Set<Role> rolesSet = new HashSet<>();
        for(Role r: user.getRoles()){
            rolesSet.add(roleService.getRole(r.getName()));
        }
        user.setRoles(rolesSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        addUser(user);
        return user;
    }
}

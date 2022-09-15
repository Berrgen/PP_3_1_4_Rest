package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public MyRestController(PasswordEncoder passwordEncoder,
                            UserService userService,
                            RoleService roleService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping ("/about")
    public User getCurrentUser(Principal principal){
//        return userRepository.findByEmail(principal.getName());
        return userService.getByUserName(principal.getName());
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.getUserById(id);

    }
    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return true;
    }

    @PutMapping("/users")
    public User updateUserPost(@RequestBody User user) {
        User userDb = null;
        if (user.getRoles().size() == 0 || user.getPassword().equals("")) {
            userDb = userService.getUserById(user.getId());
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
        userService.editUser(user);
        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        Set<Role> rolesSet = new HashSet<>();
        for(Role r: user.getRoles()){
            rolesSet.add(roleService.getRole(r.getName()));
        }
        user.setRoles(rolesSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return user;
    }

}

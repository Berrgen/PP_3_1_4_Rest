package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;


import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

//    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
//    private final RoleService roleService;
//    private final UserRepository userRepository;

    @Autowired
    public MyRestController(/*PasswordEncoder passwordEncoder,*/
                            UserService userService/*,*/
                            /*RoleService roleService, UserRepository userRepository*/) {
//        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
//        this.roleService = roleService;
//        this.userRepository = userRepository;
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
    public User updateUserPut (@RequestBody User user) {
        userService.updateUserPutMap(user);
        return user;
    }

    @PostMapping("/users")
    public User addUserPost (@RequestBody User user){
        userService.addUserPostMap(user);
        return user;
    }

}

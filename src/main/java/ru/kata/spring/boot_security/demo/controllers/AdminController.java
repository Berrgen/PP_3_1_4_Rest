package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserService userService,
                           RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String blockMain(@ModelAttribute("user") User user,
                            Model model,
                            Principal principal) {
        model.addAttribute("userPrincipal", userRepository.findByEmail(principal.getName()));
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getRoleList());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/admin-page";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUser(@PathVariable("id") Long id,Model model) {
        User user = userService.getUserById(id);
        user.setPassword("");
        List<Role> listRoles = roleService.getRoleList();
        model.addAttribute("user",user);
        model.addAttribute("listRoles", listRoles);
        return "admin/edit-user";
    }

    @PutMapping(value = "/edit/{id}")
    public String editUser(@ModelAttribute("user") User userForm,
                                 @PathVariable("id") Long id,
                                 @RequestParam(name = "role", required = false) String[] roles) {
        Set<Role> rolesSet = new HashSet<>();
        if (roles != null) {
            for (String s : roles) {
                rolesSet.add(roleService.getRole(s));
            }
            userForm.setRoles(rolesSet);
            if (userForm.getPassword() != null) {
                userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
            } else {
                userForm.setPassword(userService.getByUserName(userForm.getEmail()).getPassword());
            }
        }
        userService.editUser(userForm);
        return "redirect:/admin";
    }

    @DeleteMapping(value = "/delete/{id}")
    public ModelAndView deleteUser(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.deleteUserById(id);
        return modelAndView;
    }

    @GetMapping("/add")
    public String addView(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", user);
        return "admin/add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(name = "role", required = false) String[] roles){
        Set<Role> rolesSet = new HashSet<>();
        for(String s: roles){
            rolesSet.add(roleService.getRole(s));
        }
        user.setRoles(rolesSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin";
    }

}


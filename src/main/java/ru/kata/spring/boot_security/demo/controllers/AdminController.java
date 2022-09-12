package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;



@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ModelAndView showAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-page");
        modelAndView.addObject("userList", userService.getAllUsers());
        return modelAndView;
    }


    @GetMapping(value = "/edit/{id}")
    public ModelAndView editUser(@PathVariable(name = "id")long id, @ModelAttribute("user") User user) {
        user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/edit-user");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PutMapping(value = "/edit/{id}")
    public ModelAndView saveUser(@PathVariable(name = "id")long id, @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.editUser(user);
        return modelAndView;
    }

    @DeleteMapping(value = "/delete/{id}")
    public ModelAndView deleteUser(@PathVariable(name = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        userService.deleteUserById(id);
        return modelAndView;
    }

    @GetMapping("/add")
    public String createUserHtml(Model model) {
        model.addAttribute("user", new User());
        return "admin/add-user";
    }

    @PostMapping(value = "/add")
    public ModelAndView addUser(@ModelAttribute(name = "firstname") String firstname,
                                @ModelAttribute(name = "lastname")String lastname,
                                @ModelAttribute(name = "age") byte age,
                                @ModelAttribute(name = "email") String email,
                                @ModelAttribute(name = "password") String password) {
        userService.saveUserWithDefaultRole(new User(firstname, lastname, age, email, password));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

}


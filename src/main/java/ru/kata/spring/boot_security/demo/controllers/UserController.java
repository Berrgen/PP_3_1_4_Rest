package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ModelAndView showAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-info");
        modelAndView.addObject("userList", userService.getAllUsers());
        return modelAndView;
    }
}

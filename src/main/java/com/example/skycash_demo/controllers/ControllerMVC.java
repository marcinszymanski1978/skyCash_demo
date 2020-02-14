package com.example.skycash_demo.controllers;

import javax.validation.Valid;

import com.example.skycash_demo.model.User;
import com.example.skycash_demo.model.UserRole;
import com.example.skycash_demo.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControllerMVC {

    private ModelService userService;

    @Autowired
    public void setUserService(ModelService userService) {
        this.userService = userService;
    }

    @GetMapping("/registerUser")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/registerUser")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindResult) {
        if(bindResult.hasErrors())
            return "registerForm";
        else {
            userService.addUserWithDefaultRole(user);
            return "registerSuccess";
        }
    }

    @GetMapping("/registerRole")
    public String registerRole(Model model) {
        model.addAttribute("userRole", new UserRole());
        return "registerFormRole";
    }

    @PostMapping("/registerRole")
    public String addRole(@ModelAttribute @Valid UserRole userRole,
                          BindingResult bindResult) {
        if(bindResult.hasErrors())
            return "registerFormRole";
        else {
            userService.addRole(userRole);
            return "registerSuccess";
        }
    }

}
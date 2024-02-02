package com.example.taskManager.controller;

import com.example.taskManager.model.User;
import com.example.taskManager.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private RegistrationService registrationService;


    @GetMapping("/register")
    public String getRegisterUser(Model model) {
        List<User> userToRegister = registrationService.getAllUsers();
        model.addAttribute("userToRegister", userToRegister);
        model.addAttribute("newUser", new User());
        return "register";
    }

    @PostMapping("/create_user")
    public String createUser(@RequestParam String username, @RequestParam String password,
                             @RequestParam String email, @RequestParam String repeatedPassword) {
        registrationService.createUser(username,password,email,repeatedPassword);
        return "redirect:/login";
    }

}





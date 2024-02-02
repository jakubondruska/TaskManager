package com.example.taskManager.controller;

import com.example.taskManager.model.User;
import com.example.taskManager.service.LoginService;
import com.example.taskManager.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {

    private final RegistrationService registrationService;
    private final LoginService loginService;

    @Autowired
    public LoginController(RegistrationService registrationService, LoginService loginService) {
        this.registrationService = registrationService;
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        List<User> userToLogIn = registrationService.getAllUsers();
        model.addAttribute("userToLogIn", userToLogIn);
        return "login";

    }

    @PostMapping("doLogin")
    public String doLogin(@RequestParam String emailOrUsername, @RequestParam String password, Model model) {

        try {
            loginService.loginUser(emailOrUsername, password);
            return "redirect:/collections";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";

        }
    }
}

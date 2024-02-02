package com.example.taskManager.service;

import com.example.taskManager.model.User;
import com.example.taskManager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepo userRepo;

    @Autowired
    public LoginService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public Optional<User> getUserByEmailOrUsername(String emailOrUsername) {
        return Optional.ofNullable(userRepo.findByEmailOrUsername(emailOrUsername,emailOrUsername));
    }



    public Optional<User> getUserByPassword(String password) {
        return Optional.ofNullable(userRepo.findByEmail(password));
    }


    public void loginUser(String emailOrUsername, String password) {
        Optional<User> userToLogin = getUserByEmailOrUsername(emailOrUsername);
        if (userToLogin.isPresent() && userToLogin.get().getPassword().equals(password)) {
            System.out.println("Success");
        } else {
            throw new IllegalArgumentException("Invalid email or username or password");
        }
    }


}

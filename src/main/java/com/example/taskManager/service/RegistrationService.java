package com.example.taskManager.service;

import com.example.taskManager.model.User;
import com.example.taskManager.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationService {

    private final UserRepo userRepo;

    @Autowired
    public RegistrationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepo.findByUsername(username));
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepo.findByEmail(email));
    }

    public void userIfExist(String username, String email) {
        if (getUserByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User with this username already exist, try something else.");
        } else if (getUserByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with this email already exist, try something else.");
        }
    }


    @Transactional
    public void createUser(String username, String password, String email,String repeatedPassword){

        //check if user exists
        userIfExist(username,email);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);

        verifyPassword(password,repeatedPassword);
        newUser.setPassword(password);

        userRepo.save(newUser);

    }

    public void verifyPassword(String password, String repeatedPassword) {
        // Check if password meets conditions
        if (!(hasUppercase(password) && hasDigit(password)
                && hasSpecialCharacter(password) && hasNumCharacters(password))) {
            // Handle the case when the password doesn't meet the criteria
            throw new IllegalArgumentException("Password does not meet the criteria.");
        } else {
            validatePasswordRepeat(password,repeatedPassword);
        }
    }
    public static void validatePasswordRepeat(String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
    }

    private boolean hasUppercase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private boolean hasNumCharacters(String password) {
        final int minLength = 8;
        return password.length() >= minLength;
    }

    private boolean hasDigit(String password) {
        return password.matches(".*\\d.*");
    }

    private boolean hasSpecialCharacter(String password) {
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = specialCharPattern.matcher(password);
        return matcher.find();
    }
}

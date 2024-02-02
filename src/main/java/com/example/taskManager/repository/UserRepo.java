package com.example.taskManager.repository;

import com.example.taskManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findByEmailOrUsername(String username,String email);


}

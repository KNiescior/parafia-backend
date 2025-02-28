package com.example.holyclick.user.util;

import com.example.holyclick.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserMapper {
    public static User mapToUser(UserDetails userDetails) {
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        return user;
    }
}
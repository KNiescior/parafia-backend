package com.example.holyclick.user.controller;

import com.example.holyclick.JwtUtil;
import com.example.holyclick.user.exception.*;
import com.example.holyclick.user.model.User;
import com.example.holyclick.user.repository.UserRepository;
import com.example.holyclick.user.response.UserLoginResponse;
import com.example.holyclick.user.response.UserRegisterResponse;
import com.example.holyclick.user.service.PasswordValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public @ResponseBody UserRegisterResponse registerUser(@RequestParam String name, @RequestParam String password, @RequestParam String repeatPassword) {
        PasswordValidatorService passwordValidatorService = new PasswordValidatorService();

        if (!passwordValidatorService.isPasswordValid(password)) {
            throw new PasswordNotValidException("Password is not valid");
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException("Passwords do not match");
        }

        if (userRepository.existsByUsername(name)) {
            throw new UserAlreadyExistException("Given user already exist");
        }

        saveUserIntoDatabase(name, password);

        return new UserRegisterResponse(HttpStatus.OK, "Successfully created user");
    }

    @GetMapping("/login")
    public @ResponseBody UserLoginResponse loginUser(@RequestParam String name, @RequestParam String password) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotExistException("Bad credentials"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException("Bad credentials");
        }

        String jwtToken = jwtUtil.generateToken(user.getUsername());


        return new UserLoginResponse(HttpStatus.OK, jwtToken);
    }

    private void saveUserIntoDatabase(String name, String password) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
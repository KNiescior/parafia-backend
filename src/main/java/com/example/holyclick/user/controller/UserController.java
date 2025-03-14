package com.example.holyclick.user.controller;

import com.example.holyclick.JwtUtil;
import com.example.holyclick.persona.service.PersonaService;
import com.example.holyclick.user.exception.*;
import com.example.holyclick.user.model.User;
import com.example.holyclick.user.repository.UserRepository;
import com.example.holyclick.user.request.LoginRequest;
import com.example.holyclick.user.request.RegisterRequest;
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

    @Autowired
    private PersonaService personaService;

    @PostMapping("/register")
    public @ResponseBody UserRegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
        PasswordValidatorService passwordValidatorService = new PasswordValidatorService();

        if (!passwordValidatorService.isPasswordValid(registerRequest.getPassword())) {
            throw new PasswordNotValidException("Password is not valid");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new PasswordsDoNotMatchException("Passwords do not match");
        }

        if (userRepository.existsByUsername(registerRequest.getName())) {
            throw new UserAlreadyExistException("Given user already exist");
        }

        saveUserIntoDatabase(registerRequest.getName(), registerRequest.getPassword());

        return new UserRegisterResponse(HttpStatus.OK, "Successfully created user");
    }

    @PostMapping("/login")
    public @ResponseBody UserLoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getName()).orElseThrow(() -> new UsernameNotExistException("Bad credentials"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("Bad credentials");
        }

        String jwtToken = jwtUtil.generateToken(user.getUsername());

        Object activePersona = personaService.getActivePersona(user);

        String role = "USER";
        
        if (activePersona != null) {
            role = activePersona.getClass().getSimpleName();
        }

        return new UserLoginResponse(HttpStatus.OK, jwtToken, role);
    }

    private void saveUserIntoDatabase(String name, String password) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
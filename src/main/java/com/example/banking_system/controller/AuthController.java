package com.example.banking_system.controller;

import com.example.banking_system.dto.LoginRequest;
import com.example.banking_system.dto.RegisterRequest;
import com.example.banking_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request) {

        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest request) {

        return userService.loginUser(request);
    }
}
package com.example.banking_system.services;

import com.example.banking_system.dto.LoginRequest;
import com.example.banking_system.dto.RegisterRequest;
import com.example.banking_system.entity.User;
import com.example.banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String registerUser(RegisterRequest request) {

        User existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser != null) {
            return "Email already exists";
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("CUSTOMER")
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public String loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            return "User not found";
        }

        boolean passwordMatch =
                passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!passwordMatch) {
            return "Invalid Password";
        }

        return "Login Successful";
    }
}
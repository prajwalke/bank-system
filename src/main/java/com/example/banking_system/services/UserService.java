package com.example.banking_system.services;

import com.example.banking_system.dto.LoginRequest;
import com.example.banking_system.dto.RegisterRequest;
import com.example.banking_system.entity.User;
import com.example.banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.banking_system.dto.LoginResponse;
import com.example.banking_system.security.JwtUtil;

@Service
public class UserService {
    @Autowired
    private JwtUtil jwtUtil;

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

    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(
                request.getEmail());

        if (user == null) {

            return LoginResponse.builder()
                    .message("User Not Found")
                    .build();
        }

        boolean passwordMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

        if (!passwordMatch) {

            return LoginResponse.builder()
                    .message("Invalid Password")
                    .build();
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .message("Login Successful")
                .build();
    }
}
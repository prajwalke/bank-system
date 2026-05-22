package com.example.banking_system.dto;

import lombok.Data;
// lombok annotation to genrate getters, setters, toStrings, equals and hshcode methods. 
// It reduces boiler code in java and makes code more readable and maintainable

@Data

// @data is annotation provided by lombok library which we called as java bean
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
}
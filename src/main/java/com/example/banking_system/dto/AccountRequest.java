package com.example.banking_system.dto;
import lombok.Data;

@Data
public class AccountRequest {
    private String accountHolderName;
    private Double initialBalance;
    private String accountType;
    
}
// This class is a Data Transfer Object (DTO) used to capture the necessary information for creating a 
// new bank account. It includes fields for the account holder's name, the initial balance, and the type of account. 
// The @Data annotation from Lombok generates getters, setters, and other utility methods automatically.

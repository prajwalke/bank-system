package com.example.banking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {

    private String accountNumber;
    private String transactionType;
    private Double amount;
    private String message;
}
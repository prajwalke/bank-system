package com.example.banking_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking_system.dto.TransactionEvent;
import com.example.banking_system.kafka.producer.KafkaProducerService;

@Service
public class TransactionService {
    

    @Autowired
    private KafkaProducerService producer;

    public void deposit(String accountNumber, Double amount) {

        // Existing deposit logic goes here

        TransactionEvent event = new TransactionEvent(
                accountNumber,
                "DEPOSIT",
                amount,
                "Deposit successful"
        );

        producer.send(event);
    }
}
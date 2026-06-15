package com.example.banking_system.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(
            topics = "transaction-events",
            groupId = "banking-group")
    public void consume(String event) {

        System.out.println("Received : " + event);
    }
}
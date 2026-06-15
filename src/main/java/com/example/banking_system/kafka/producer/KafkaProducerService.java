package com.example.banking_system.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.banking_system.dto.TransactionEvent;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "transaction-events";

    @Autowired
    private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void send(TransactionEvent event) {

        kafkaTemplate.send(TOPIC, event);
    }
}
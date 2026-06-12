package com.example.banking_system.repository;

import com.example.banking_system.entity.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository
                extends JpaRepository<Transaction, Long> {   // jpa repository for transaction enity.
        List<Transaction> findByAccountNumber(
                        String accountNumber);

}

/*
 * List<Transaction> findByAccountNumber(
 * String accountNumber
 * );
 */
package com.example.banking_system.repository;
import com.example.banking_system.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);

    
} 
// This interface extends JpaRepository, providing CRUD operations for Account entities.
// It also includes a custom method findByAccountNumber to retrieve an account based on its account number.
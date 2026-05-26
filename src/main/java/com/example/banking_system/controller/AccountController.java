package com.example.banking_system.controller;

import com.example.banking_system.dto.AccountRequest;
import com.example.banking_system.dto.ApiResponse;
import com.example.banking_system.dto.TransactionRequest;
import com.example.banking_system.dto.TransferRequest;
import com.example.banking_system.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public String createAccount(
            @RequestBody AccountRequest request) {

        return accountService.createAccount(request);
    }

    @PostMapping("/deposit")
    public ApiResponse deposit(
            @RequestBody TransactionRequest request) {

        return accountService.deposit(request);
    }

    @PostMapping("/withdraw")
    public ApiResponse withdraw(
            @RequestBody TransactionRequest request) {

        return accountService.withdraw(request);
    }

    @PostMapping("/transfer")
    public ApiResponse transfer(
            @RequestBody TransferRequest request) {

        return accountService.transfer(request);
    }
}
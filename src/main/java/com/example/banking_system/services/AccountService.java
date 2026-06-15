package com.example.banking_system.services;

import com.example.banking_system.audit.AuditLogService;
import com.example.banking_system.dto.AccountRequest;
import com.example.banking_system.dto.ApiResponse;
import com.example.banking_system.dto.TransactionEvent;
import com.example.banking_system.dto.TransactionRequest;
import com.example.banking_system.dto.TransferRequest;
import com.example.banking_system.entity.Account;
import com.example.banking_system.entity.Transaction;
import com.example.banking_system.exception.InsufficientBalanceException;
import com.example.banking_system.exception.ResourceNotFoundException;
import com.example.banking_system.kafka.producer.KafkaProducerService;
import com.example.banking_system.repository.AccountRepository;
import com.example.banking_system.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {
        @Autowired
private KafkaProducerService producer;

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private TransactionRepository transactionRepository;

        @Autowired
        private AuditLogService auditLogService;

        /* -------------------- CREATE ACCOUNT -------------------- */

        public String createAccount(AccountRequest request) {

                String accountNumber = "ACC" + new Random().nextInt(999999);

                Account account = Account.builder()
                                .accountNumber(accountNumber)
                                .accountHolderName(request.getAccountHolderName())
                                .balance(request.getInitialBalance())
                                .accountType(request.getAccountType())
                                .build();

                accountRepository.save(account);

                auditLogService.log(
                                request.getAccountHolderName(),
                                "CREATE_ACCOUNT",
                                "/account/create",
                                "SUCCESS");

                return "Account Created Successfully : " + accountNumber;
        }

        /* -------------------- DEPOSIT -------------------- */

        public ApiResponse deposit(TransactionRequest request) {

                Account account = accountRepository.findByAccountNumber(
                                request.getAccountNumber());

                if (account == null) {
                        throw new ResourceNotFoundException(
                                        "Account Not Found");
                }

                account.setBalance(
                                account.getBalance() + request.getAmount());

                accountRepository.save(account);

                Transaction transaction = Transaction.builder()
                                .accountNumber(account.getAccountNumber())
                                .transactionType("DEPOSIT")
                                .amount(request.getAmount())
                                .transactionTime(LocalDateTime.now())
                                .build();

                transactionRepository.save(transaction);
                TransactionEvent event = new TransactionEvent(
        account.getAccountNumber(),
        "DEPOSIT",
        request.getAmount(),
        "Deposit successful"
);

producer.send(event);

                auditLogService.log(
                                account.getAccountNumber(),
                                "DEPOSIT",
                                "/account/deposit",
                                "SUCCESS");

                return ApiResponse.builder()
                                .status("SUCCESS")
                                .message("Amount Deposited Successfully")
                                .timestamp(LocalDateTime.now())
                                .build();
        }

        /* -------------------- WITHDRAW -------------------- */

        public ApiResponse withdraw(TransactionRequest request) {

                Account account = accountRepository.findByAccountNumber(
                                request.getAccountNumber());

                if (account == null) {
                        throw new ResourceNotFoundException(
                                        "Account Not Found");
                }

                if (account.getBalance() < request.getAmount()) {
                        throw new InsufficientBalanceException(
                                        "Insufficient Balance");
                }

                account.setBalance(
                                account.getBalance() - request.getAmount());

                accountRepository.save(account);

                Transaction transaction = Transaction.builder()
                                .accountNumber(account.getAccountNumber())
                                .transactionType("WITHDRAW")
                                .amount(request.getAmount())
                                .transactionTime(LocalDateTime.now())
                                .build();

                transactionRepository.save(transaction);
                TransactionEvent event = new TransactionEvent(
        account.getAccountNumber(),
        "WITHDRAW",
        request.getAmount(),
        "Withdraw successful"
);

producer.send(event);

                auditLogService.log(
                                account.getAccountNumber(),
                                "WITHDRAW",
                                "/account/withdraw",
                                "SUCCESS");

                return ApiResponse.builder()
                                .status("SUCCESS")
                                .message("Amount Withdrawn Successfully")
                                .timestamp(LocalDateTime.now())
                                .build();
        }

        /* -------------------- TRANSFER -------------------- */

        public ApiResponse transfer(TransferRequest request) {

                Account sender = accountRepository.findByAccountNumber(
                                request.getFromAccount());

                Account receiver = accountRepository.findByAccountNumber(
                                request.getToAccount());

                if (sender == null || receiver == null) {
                        throw new ResourceNotFoundException(
                                        "Invalid Account Details");
                }

                if (sender.getBalance() < request.getAmount()) {
                        throw new InsufficientBalanceException(
                                        "Insufficient Balance");
                }

                sender.setBalance(
                                sender.getBalance() - request.getAmount());

                receiver.setBalance(
                                receiver.getBalance() + request.getAmount());

                accountRepository.save(sender);
                accountRepository.save(receiver);

                Transaction senderTransaction = Transaction.builder()
                                .accountNumber(sender.getAccountNumber())
                                .transactionType("TRANSFER_DEBIT")
                                .amount(request.getAmount())
                                .transactionTime(LocalDateTime.now())
                                .build();

                Transaction receiverTransaction = Transaction.builder()
                                .accountNumber(receiver.getAccountNumber())
                                .transactionType("TRANSFER_CREDIT")
                                .amount(request.getAmount())
                                .transactionTime(LocalDateTime.now())
                                .build();

                transactionRepository.save(senderTransaction);
                transactionRepository.save(receiverTransaction);
                TransactionEvent event = new TransactionEvent(
        sender.getAccountNumber(),
        "TRANSFER",
        request.getAmount(),
        "Transfer successful"
);

producer.send(event);

                auditLogService.log(
                                sender.getAccountNumber(),
                                "TRANSFER",
                                "/account/transfer",
                                "SUCCESS");

                return ApiResponse.builder()
                                .status("SUCCESS")
                                .message("Fund Transfer Successful")
                                .timestamp(LocalDateTime.now())
                                .build();

        }

        public List<Transaction> getStatement(
                        String accountNumber) {

                return transactionRepository
                                .findByAccountNumber(
                                                accountNumber);
        }

}

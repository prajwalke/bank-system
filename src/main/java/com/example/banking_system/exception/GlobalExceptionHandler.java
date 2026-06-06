package com.example.banking_system.exception;

import com.example.banking_system.dto.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public ResponseEntity<ApiResponse>
    handleResourceNotFound(
            ResourceNotFoundException ex
    ) {

        ApiResponse response =
                ApiResponse.builder()
                        .status("ERROR")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(
            InsufficientBalanceException.class
    )
    public ResponseEntity<ApiResponse>
    handleInsufficientBalance(
            InsufficientBalanceException ex
    ) {

        ApiResponse response =
                ApiResponse.builder()
                        .status("ERROR")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }
}
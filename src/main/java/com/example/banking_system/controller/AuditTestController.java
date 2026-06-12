package com.example.banking_system.controller;

import com.example.banking_system.audit.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditTestController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/audit-test")
    public String testAudit() {

        auditLogService.log(
                "prajwal@gmail.com",
                "LOGIN",
                "/auth/login",
                "SUCCESS"
        );

        return "Audit Log Saved";
    }
}
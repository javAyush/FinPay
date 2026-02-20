package com.payment.engine.controller;

import com.payment.engine.Dto.BalanceResponse;
import com.payment.engine.service.AccountServiceImpl;
import com.payment.engine.service.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable Long accountId) {

        return ResponseEntity.ok(accountService.getBalance(accountId));
    }
}

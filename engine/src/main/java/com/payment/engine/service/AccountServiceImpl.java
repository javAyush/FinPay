package com.payment.engine.service;

import com.payment.engine.Dto.BalanceResponse;
import com.payment.engine.entity.Account;
import com.payment.engine.repository.AccountRepository;
import com.payment.engine.service.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public BalanceResponse getBalance(Long accountId) {

        Account account = getAccountOrThrow(accountId);
        return BalanceResponse.builder()
                .accountId(accountId)
                .balance(account.getBalance())
                .build();
    }

    @Override
    public Account getAccountOrThrow(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

}

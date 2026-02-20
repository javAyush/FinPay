package com.payment.engine.service.interfaces;


import com.payment.engine.Dto.BalanceResponse;
import com.payment.engine.entity.Account;

public interface AccountService {

    BalanceResponse getBalance(Long accountId);
    Account getAccountOrThrow(Long accountId);
}
package com.payment.engine.service;

import com.payment.engine.Dto.PaymentRequest;
import com.payment.engine.Dto.PaymentResponse;
import com.payment.engine.entity.Account;
import com.payment.engine.entity.LedgerEntry;
import com.payment.engine.entity.PaymentStatus;
import com.payment.engine.entity.Transaction;
import com.payment.engine.repository.LedgerEntryRepository;
import com.payment.engine.repository.TransactionRepository;
import com.payment.engine.service.interfaces.AccountService;
import com.payment.engine.service.interfaces.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    @Override
    public PaymentResponse authorize(PaymentRequest request) {
        Account account = accountService.getAccountOrThrow(request.getAccountId());

        // check if account balance is greater that amount to be deducted
        if(account.getBalance().compareTo(request.getAmount())<0){
            throw new RuntimeException("Account balance is insufficient");
        }

        account.setBalance(account.getBalance() - request.getAmount());
        String transactionId = UUID.randomUUID().toString();

        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .accountId(request.getAccountId())
                .amount(account.getBalance())
                .createdAt(System.currentTimeMillis())
                .status(PaymentStatus.AUTHORIZED)
                .build();

        transactionRepository.save(transaction);

        LedgerEntry ledgerEntry = LedgerEntry.builder()
                .transactionId(transactionId)
                .debit(BigDecimal.valueOf(request.getAmount()))
                .credit(BigDecimal.ZERO)
                .createdAt(System.currentTimeMillis())
                .build();

        ledgerEntryRepository.save(ledgerEntry);


        return PaymentResponse.builder()
                .transactionId(transactionId)
                .status(PaymentStatus.AUTHORIZED)
                .remainingBalance(transaction.getAmount())
                .build();
    }

    @Override
    public Transaction getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

}

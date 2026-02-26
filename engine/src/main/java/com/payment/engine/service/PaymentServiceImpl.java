package com.payment.engine.service;

import com.payment.engine.Dto.FraudRequest;
import com.payment.engine.Dto.FraudResponse;
import com.payment.engine.Dto.PaymentRequest;
import com.payment.engine.Dto.PaymentResponse;
import com.payment.engine.config.FraudClient;
import com.payment.engine.entity.*;
import com.payment.engine.repository.IdempotentRepository;
import com.payment.engine.repository.LedgerEntryRepository;
import com.payment.engine.repository.TransactionRepository;
import com.payment.engine.service.interfaces.AccountService;
import com.payment.engine.service.interfaces.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;
    private final IdempotentRepository idempotentRepository;
    private final ObjectMapper objectMapper;
    private final FraudClient fraudClient;

    @Override
    @Transactional
    public PaymentResponse authorize(String idempotency_key, PaymentRequest request) {
        Account account = accountService.getAccountOrThrow(request.getAccountId());
        String transactionId = UUID.randomUUID().toString();
        //check if already transaction present from this key
        Optional<Idempotency> existingKey = idempotentRepository.findByIdempotencyKey(idempotency_key);

        log.info(String.valueOf(existingKey));
        if(existingKey.isPresent()){
            try{
                return objectMapper.readValue(
                        existingKey.get().getResponsePayload(),
                        PaymentResponse.class);
            } catch (Exception e){
                throw new RuntimeException("Failed to deserialize the response");
            }
        }

        try{

        // check if account balance is lesser that amount to be deducted
        if(account.getBalance().compareTo(request.getAmount())<0){
            return new PaymentResponse(
                    transactionId,
                    PaymentStatus.INSUFFICIENT_BALANCE,
                    account.getBalance()
            );
        }
        //checking if its not fraud
        FraudResponse fraudResponse = fraudClient.checkFraud(
                new FraudRequest(request.getAccountId(), request.getAmount())
        );

        if (fraudResponse.isFraudStatus()) {
            throw new RuntimeException("Transaction flagged as fraud");
        }
        //set Balance
        account.setBalance(account.getBalance() - request.getAmount());



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

        PaymentResponse response = PaymentResponse.builder()
                .remainingBalance(account.getBalance())
                .transactionId(transactionId)
                .status(PaymentStatus.AUTHORIZED)
                .build();

        String responseJson = objectMapper.writeValueAsString(response);
        Idempotency idempotency = Idempotency.builder()
                .idempotencyKey(idempotency_key)
                .transaction_id(transactionId)
                .responsePayload(responseJson)
                .createdAt(System.currentTimeMillis())
                .build();
        idempotentRepository.save(idempotency);

        return response;

    }catch(ObjectOptimisticLockingFailureException o){
            throw new RuntimeException("Concurrent Update detected");
        }
        catch (Exception e){
            throw new RuntimeException("Payment Processing Failed",e);
        }
    }

    @Override
    public Transaction getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

}

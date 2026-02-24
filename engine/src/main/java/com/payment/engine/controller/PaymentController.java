package com.payment.engine.controller;

import com.payment.engine.Dto.PaymentRequest;
import com.payment.engine.Dto.PaymentResponse;
import com.payment.engine.entity.Transaction;
import com.payment.engine.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> authorizePayment(
            @RequestHeader("Idempotency-Key") String idempotency_key,
            @Valid @RequestBody PaymentRequest request){
       return ResponseEntity.status(HttpStatus.ACCEPTED)
               .body(paymentService.authorize(idempotency_key,request));
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getPaymentStatus(
            @PathVariable String transactionId
    ){
        return ResponseEntity.ok(paymentService.getTransaction(transactionId));
    }
}

package com.payment.engine.Dto;

import com.payment.engine.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {

    private String transactionId;
    private PaymentStatus status;
    private Double remainingBalance;
}


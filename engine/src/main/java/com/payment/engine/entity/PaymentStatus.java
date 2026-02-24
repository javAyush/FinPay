package com.payment.engine.entity;


import lombok.Getter;

@Getter
public enum PaymentStatus {
    AUTHORIZED,
    INSUFFICIENT_BALANCE,
    FAILED
}

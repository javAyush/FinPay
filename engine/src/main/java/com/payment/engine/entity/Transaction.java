package com.payment.engine.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Transactions")
@Getter
@Setter
@Builder
public class Transaction {

    @Id
    private String id;   // UUID

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private long createdAt;

}



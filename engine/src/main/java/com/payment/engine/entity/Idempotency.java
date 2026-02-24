package com.payment.engine.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Entity
@Table(name = "Idempotent_Keys")
@Builder
@RequiredArgsConstructor
@Getter
public class Idempotency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "transaction_id",nullable = false)
    private String transaction_id;

    @Column(name ="Response Payload", columnDefinition = "TEXT")
    private String responsePayload;

    private long createdAt;
}

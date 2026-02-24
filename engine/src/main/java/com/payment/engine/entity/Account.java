package com.payment.engine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Accounts")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double balance;

    @Version
    private Long version;
}

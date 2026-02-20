package com.payment.engine.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BalanceResponse {

    private Long accountId;
    private Double  balance;
}


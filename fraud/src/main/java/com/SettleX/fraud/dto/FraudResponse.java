package com.SettleX.fraud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FraudResponse {
    private boolean fraudStatus;
    private Integer riskScore;
    private String message;
}

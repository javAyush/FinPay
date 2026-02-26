package com.payment.engine.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FraudResponse {

    private boolean fraudStatus;
    private Integer riskScore;
    private String message;

}

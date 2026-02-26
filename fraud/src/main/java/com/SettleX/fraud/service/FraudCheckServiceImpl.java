package com.SettleX.fraud.service;

import com.SettleX.fraud.dto.FraudRequest;
import com.SettleX.fraud.dto.FraudResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FraudCheckServiceImpl implements FraudCheckService {

    private final Random random = new Random();

    @Override
    public FraudResponse checkIfFraud(FraudRequest request) {

        if (random.nextInt(10) < 2) {
            try {
                Thread.sleep(3000); // simulate slow dependency
            } catch (InterruptedException ignored) {}
        }

        if (random.nextInt(10) == 0) {
            throw new RuntimeException("Fraud service failure");
        }

        int riskScore = random.nextInt(100);

        boolean fraudDetected =
                request.getAmount().compareTo(new Double("50000")) > 0
                        || riskScore > 85;

        return FraudResponse.builder()
                .fraudStatus(fraudDetected)
                .riskScore(riskScore)
                .message(fraudDetected ? "High risk transaction" : "Approved")
                .build();
    }
}


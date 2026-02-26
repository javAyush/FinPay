package com.payment.engine.config;

import com.payment.engine.Dto.FraudRequest;
import com.payment.engine.Dto.FraudResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudClient {

    private final WebClient webClient;

    @CircuitBreaker(name = "fraudService" , fallbackMethod = "fallbackFraud")
    public FraudResponse checkFraud(FraudRequest request){

        return webClient.post()
                .uri("/fraud/check")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(FraudResponse.class)
                .block();
    }

    public FraudResponse fallbackFraud(FraudRequest request, Throwable ex) {
        throw new RuntimeException("Fraud service unavailable. Try later.");
    }
}

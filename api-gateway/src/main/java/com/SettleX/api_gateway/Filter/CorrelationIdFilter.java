package com.SettleX.api_gateway.Filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class CorrelationIdFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String correlationId = UUID.randomUUID().toString();

        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-Correlation-Id", correlationId)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }
}

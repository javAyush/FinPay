package com.SettleX.api_gateway.Autentication;

import com.SettleX.api_gateway.Utility.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JWTUtility jwtUtility;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange , GatewayFilterChain gatewayFilterChain){

        String path = serverWebExchange.getRequest().getURI().getPath();
        if (path.contains("/auth")) {
            return gatewayFilterChain.filter(serverWebExchange);
        }

        String authHeader = serverWebExchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return serverWebExchange.getResponse().setComplete();
        }
        String token = authHeader.substring(7);
        try {
            jwtUtility.validateToken(token);
        } catch (Exception e) {
            serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return serverWebExchange.getResponse().setComplete();
        }

        return gatewayFilterChain.filter(serverWebExchange);

    }

}

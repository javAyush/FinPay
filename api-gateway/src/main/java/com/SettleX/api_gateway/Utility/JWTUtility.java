package com.SettleX.api_gateway.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtility {

    private final String SECRET = "my-secret-key-my-secret-key-my-secret-key";

    public Claims validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() * 60*60*10))
                .signWith(SignatureAlgorithm.ES256,SECRET.getBytes())
                .compact();
    }
}

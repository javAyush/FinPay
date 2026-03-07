package com.SettleX.api_gateway.Utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JWTUtility {

    private final String secret = "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    public void validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public String  generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*10))
                .signWith(key)
                .compact();
    }
}

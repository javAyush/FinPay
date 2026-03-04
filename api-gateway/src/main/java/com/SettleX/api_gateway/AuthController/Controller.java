package com.SettleX.api_gateway.AuthController;

import com.SettleX.api_gateway.Utility.JWTUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Controller {

    private final JWTUtility jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(token);
    }
}

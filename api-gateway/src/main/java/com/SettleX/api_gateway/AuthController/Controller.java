package com.SettleX.api_gateway.AuthController;

import com.SettleX.api_gateway.Utility.JWTUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final JWTUtility jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        try {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            log.error("Error in creating token", e);
            return new ResponseEntity<>("Error in token generation", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/token")
    public String generateToken() {
        return "test-token";
    }
}

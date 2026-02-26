package com.SettleX.fraud.controller;

import com.SettleX.fraud.dto.FraudRequest;
import com.SettleX.fraud.dto.FraudResponse;
import com.SettleX.fraud.service.FraudCheckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/fraud")
@RestController
@RequiredArgsConstructor
public class FraudController {

    private FraudCheckService fraudCheckService;

    @PostMapping("/check")
    public ResponseEntity<FraudResponse> checkFraud(
            @Valid @RequestBody FraudRequest fraudRequest
            ){
        return ResponseEntity.ok(fraudCheckService.checkIfFraud(fraudRequest));
    }
}

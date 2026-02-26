package com.SettleX.fraud.service;

import com.SettleX.fraud.dto.FraudRequest;
import com.SettleX.fraud.dto.FraudResponse;

public interface FraudCheckService {
     FraudResponse checkIfFraud(FraudRequest fraudRequest);

}

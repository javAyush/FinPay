package com.payment.engine.service.interfaces;

import com.payment.engine.Dto.PaymentRequest;
import com.payment.engine.Dto.PaymentResponse;
import com.payment.engine.entity.Transaction;


public interface PaymentService {

     PaymentResponse authorize(PaymentRequest request);

     Transaction getTransaction(String transactionId);
}

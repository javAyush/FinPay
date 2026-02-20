package com.payment.engine.repository;

import com.payment.engine.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction , String > {
}

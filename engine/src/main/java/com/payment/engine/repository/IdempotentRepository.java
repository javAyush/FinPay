package com.payment.engine.repository;

import com.payment.engine.entity.Idempotency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IdempotentRepository extends JpaRepository<Idempotency, Long> {

    Optional<Idempotency> findByIdempotency(String idempotencyKey);
}

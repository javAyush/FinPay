package com.payment.engine.repository;

import com.payment.engine.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry , Long> {
}

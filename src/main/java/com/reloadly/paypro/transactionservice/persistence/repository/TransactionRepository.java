package com.reloadly.paypro.transactionservice.persistence.repository;

import com.reloadly.paypro.transactionservice.persistence.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(String reference);

}

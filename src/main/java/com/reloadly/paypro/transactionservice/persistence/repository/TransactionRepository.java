package com.reloadly.paypro.transactionservice.persistence.repository;

import com.reloadly.paypro.transactionservice.enums.RecordStatus;
import com.reloadly.paypro.transactionservice.persistence.model.Transaction;
import com.reloadly.paypro.transactionservice.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(String reference);

    List<Transaction> getAllBySenderAndRecordStatusOrderByDateCreatedDesc(User sender, RecordStatus recordStatus);

}

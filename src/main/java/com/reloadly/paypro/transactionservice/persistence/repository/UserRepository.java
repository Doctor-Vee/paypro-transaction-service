package com.reloadly.paypro.transactionservice.persistence.repository;

import com.reloadly.paypro.transactionservice.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountNumber(String accountNumber);

}
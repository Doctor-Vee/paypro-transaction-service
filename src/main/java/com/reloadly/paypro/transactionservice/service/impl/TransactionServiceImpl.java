package com.reloadly.paypro.transactionservice.service.impl;

import com.reloadly.paypro.transactionservice.enums.RecordStatus;
import com.reloadly.paypro.transactionservice.enums.TransactionStatus;
import com.reloadly.paypro.transactionservice.exception.BadRequestException;
import com.reloadly.paypro.transactionservice.exception.NotFoundException;
import com.reloadly.paypro.transactionservice.exception.UnauthorisedAccessException;
import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.payload.request.LoginRequest;
import com.reloadly.paypro.transactionservice.payload.request.LoginResponse;
import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;
import com.reloadly.paypro.transactionservice.persistence.model.Transaction;
import com.reloadly.paypro.transactionservice.persistence.model.User;
import com.reloadly.paypro.transactionservice.persistence.repository.TransactionRepository;
import com.reloadly.paypro.transactionservice.persistence.repository.UserRepository;
import com.reloadly.paypro.transactionservice.persistence.service.TransactionDTOService;
import com.reloadly.paypro.transactionservice.service.TransactionService;
import com.reloadly.paypro.transactionservice.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionDTOService transactionDTOService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String processFundTransfer(String userAccountNumber, TransferRequest transferRequest) {
        User sender = userRepository.findByAccountNumber(userAccountNumber).orElseThrow(() -> new UnauthorisedAccessException("Unauthorised Access - User not found"));
        if(ObjectUtils.isEmpty(transferRequest.getAccountNumber()) || transferRequest.getAmount() <= 0.0) throw new BadRequestException("Missing required details");
        String accountNumber = transferRequest.getAccountNumber();
        BigDecimal amount = BigDecimal.valueOf(transferRequest.getAmount());
        if(amount.compareTo(sender.getBalance()) > 0) throw new BadRequestException("Insufficient funds");
        User receiver = userRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundException("Invalid account number supplied"));
        String narration = ObjectUtils.isEmpty(transferRequest.getNarration()) ? String.format("Fund transfer of %s to %s", amount, receiver.getUsername()) : transferRequest.getNarration();
        if(sender.getUsername().equals(receiver.getUsername())) throw new BadRequestException("You can't send money to yourself... Please select a different account number");
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));
        Transaction transaction = new Transaction(sender, receiver, amount, narration, TransactionStatus.SUCCESSFUL, AppUtil.generate20DigitTransactionReference());
        transactionRepository.save(transaction);
        userRepository.save(sender);
        userRepository.save(receiver);
        return String.format("Successfully transferred %s to %s", amount, receiver.getUsername());
    }


    @Override
    public List<TransferDTO> getUserTransactionHistory(String userAccountNumber) {
        User sender = userRepository.findByAccountNumber(userAccountNumber).orElseThrow(() -> new UnauthorisedAccessException("Unauthorised Access - User not found"));
        return transactionRepository.getAllBySenderAndRecordStatusOrderByDateCreatedDesc(sender, RecordStatus.ACTIVE).stream()
                .map(transactionDTOService::fromTransactionToDTO).collect(Collectors.toList());
    }

    @Override
    public TransferDTO getUserTransaction(String userAccountNumber, String reference) {
        User sender = userRepository.findByAccountNumber(userAccountNumber).orElseThrow(() -> new UnauthorisedAccessException("Unauthorised Access - User not found"));
        Transaction transaction = transactionRepository.findByTransactionReference(reference).orElseThrow(() -> new NotFoundException("Invalid transaction reference supplied"));
        if(!sender.getUsername().equals(transaction.getSender().getUsername())) throw new UnauthorisedAccessException("This transaction was not done by you");
        return transactionDTOService.fromTransactionToDTO(transaction);
    }

    @Override
    public LoginResponse callAccountService(LoginRequest loginRequest) {
        LoginResponse loginResponse = restTemplate.postForObject("http://paypro-account-service/api/v1/auth/login", loginRequest, LoginResponse.class);
        return loginResponse;
    }
}

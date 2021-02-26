package com.reloadly.paypro.transactionservice.service;

import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.payload.request.LoginRequest;
import com.reloadly.paypro.transactionservice.payload.request.LoginResponse;
import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;

import java.util.List;

public interface TransactionService {
    String processFundTransfer(String userAccountNumber, TransferRequest transferRequest);

    List<TransferDTO> getUserTransactionHistory(String userAccountNumber);

    TransferDTO getUserTransaction(String userAccountNumber, String reference);

    LoginResponse callAccountService(LoginRequest loginRequest);
}

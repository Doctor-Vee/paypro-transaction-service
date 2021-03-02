package com.reloadly.paypro.transactionservice.service;

import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;

import java.util.List;

public interface TransactionService {
    String processFundTransfer(String username, TransferRequest transferRequest);

    List<TransferDTO> getUserTransactionHistory(String username);

    TransferDTO getUserTransaction(String username, String reference);
}

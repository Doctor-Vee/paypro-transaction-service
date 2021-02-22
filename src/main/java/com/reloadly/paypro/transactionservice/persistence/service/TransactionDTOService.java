package com.reloadly.paypro.transactionservice.persistence.service;

import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.persistence.model.Transaction;

public interface TransactionDTOService {
    TransferDTO fromTransactionToDTO(Transaction transaction);
}

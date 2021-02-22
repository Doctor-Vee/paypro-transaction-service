package com.reloadly.paypro.transactionservice.persistence.service.impl;

import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.persistence.model.Transaction;
import com.reloadly.paypro.transactionservice.persistence.model.User;
import com.reloadly.paypro.transactionservice.persistence.service.TransactionDTOService;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class TransactionDTOServiceImpl implements TransactionDTOService {
    @Override
    public TransferDTO fromTransactionToDTO(Transaction transaction) {
        TransferDTO transferDTO = new TransferDTO();
        User receiver = transaction.getReceiver();
        transferDTO.setReceiverUsername(receiver.getUsername());
        transferDTO.setReceiverAccountNumber(receiver.getAccountNumber());
        transferDTO.setAmount(transaction.getAmount().doubleValue());
        transferDTO.setReference(transaction.getTransactionReference());
        transferDTO.setNarration(transaction.getNarration());
        transferDTO.setTransactionDate(transaction.getDateCreated().toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return transferDTO;
    }
}

package com.reloadly.paypro.transactionservice.payload.dto;

import lombok.Data;

@Data
public class TransferDTO {
    private String receiverUsername;

    private String receiverAccountNumber;

    private Double amount;

    private String narration;

    private String reference;

    private String transactionDate;

}

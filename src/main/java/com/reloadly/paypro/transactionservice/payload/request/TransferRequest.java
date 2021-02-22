package com.reloadly.paypro.transactionservice.payload.request;

import lombok.Data;

@Data
public class TransferRequest {

    private String accountNumber;

    private double amount;

    private String narration;

}

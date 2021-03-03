package com.reloadly.paypro.transactionservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {

    private String accountNumber;

    private double amount;

    private String narration;

}

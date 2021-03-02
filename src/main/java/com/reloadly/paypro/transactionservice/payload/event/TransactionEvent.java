package com.reloadly.paypro.transactionservice.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionEvent {

    private String senderName;

    private String senderEmail;

    private String receiverName;

    private String receiverEmail;

    private BigDecimal amount;

    private String narration;
}

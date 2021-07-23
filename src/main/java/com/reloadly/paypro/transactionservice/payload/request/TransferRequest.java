package com.reloadly.paypro.transactionservice.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(description = "Transfer Request")
public class TransferRequest {

    @ApiModelProperty(notes = "The account number of the receiver")
    private String accountNumber;

    private double amount;

    private String narration;

}

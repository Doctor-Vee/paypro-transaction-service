package com.reloadly.paypro.transactionservice.utils;

import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;
import com.reloadly.paypro.transactionservice.persistence.model.Transaction;
import com.reloadly.paypro.transactionservice.persistence.model.User;

import java.math.BigDecimal;

public class TestModels {

    public static TransferRequest createTransferRequest(){
        return new TransferRequest("000345123", 450.23, "Partial Payment");
    }

    public static TransferRequest createTransferRequestWithoutAccountNumber(){
        return new TransferRequest(null, 450.23, "Partial Payment");
    }

    public static TransferRequest createTransferRequestWithInvalidAmount(){
        return new TransferRequest("000345123", 0.0, "Partial Payment");
    }

    public static User createDoctorVeeUser(){
        return new User("ovisco360@gmail.com", "doctorvee", "08085492459", "0001234567", BigDecimal.valueOf(10000));
    }

    public static User createVictorUser(){
        return new User("victor.chinewubeze@gmail.com", "victor", "09053969127", "0002345678", BigDecimal.valueOf(10000));
    }
}

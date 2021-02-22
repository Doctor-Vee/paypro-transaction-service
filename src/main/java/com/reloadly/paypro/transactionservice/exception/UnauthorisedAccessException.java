package com.reloadly.paypro.transactionservice.exception;

public class UnauthorisedAccessException extends RuntimeException{
    public UnauthorisedAccessException(String message){
        super(message);
    }
}

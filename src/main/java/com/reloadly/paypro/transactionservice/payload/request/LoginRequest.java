package com.reloadly.paypro.transactionservice.payload.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;

}

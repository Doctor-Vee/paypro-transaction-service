package com.reloadly.paypro.transactionservice.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateEvent {

    private String email;

    private String username;

    private String phoneNumber;

}

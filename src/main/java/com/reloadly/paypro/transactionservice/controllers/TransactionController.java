package com.reloadly.paypro.transactionservice.controllers;

import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.payload.request.LoginRequest;
import com.reloadly.paypro.transactionservice.payload.request.LoginResponse;
import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;
import com.reloadly.paypro.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer-funds")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest transferRequest){
        //TODO: Add @AuthenticationPrincipal AuthenticatedUserDetails userDetails at the top here and see how to connect it to the account service
        String userAccountNumber = "0123456789";
        String response = transactionService.processFundTransfer(userAccountNumber, transferRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransferDTO>> getTransactionHistory(){
        //TODO: Add @AuthenticationPrincipal AuthenticatedUserDetails userDetails at the top here and see how to connect it to the account service
        String userAccountNumber = "0123456789";
        List<TransferDTO> transferDTOList = transactionService.getUserTransactionHistory(userAccountNumber);
        return ResponseEntity.ok(transferDTOList);
    }

    @GetMapping("/{transactionReference}")
    public ResponseEntity<TransferDTO> getTransaction(@PathVariable String transactionReference){
    //TODO: Add @AuthenticationPrincipal AuthenticatedUserDetails userDetails at the top here and see how to connect it to the account service
    String userAccountNumber = "0123456789";
    TransferDTO transferDTO = transactionService.getUserTransaction(userAccountNumber, transactionReference);
    return ResponseEntity.ok(transferDTO);
    }

    @PostMapping("/account-jwt")
    public ResponseEntity<LoginResponse> callAccountService(@RequestBody LoginRequest loginRequest){
        LoginResponse response = transactionService.callAccountService(loginRequest);
        return ResponseEntity.ok(response);
    }

}

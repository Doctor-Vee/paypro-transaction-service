package com.reloadly.paypro.transactionservice.controllers;

import com.reloadly.paypro.transactionservice.payload.dto.TransferDTO;
import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;
import com.reloadly.paypro.transactionservice.security.AuthenticatedUserDetails;
import com.reloadly.paypro.transactionservice.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/transactions", headers = {"Authorization"})
@Api(tags = {"Transaction Controller"})
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transfer-funds")
    @ApiOperation(value = "The endpoint for transferring funds", notes = "Please ensure to pass all the required info")
    public ResponseEntity<String> transferFunds(@ApiIgnore @AuthenticationPrincipal AuthenticatedUserDetails userDetails,
                                                @ApiParam(value = "The details of the transfer request")
                                                @RequestBody TransferRequest transferRequest) {
        String response = transactionService.processFundTransfer(userDetails.getUsername(), transferRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    @ApiOperation("The endpoint for displaying a list of transactions done by the authenticated user")
    public ResponseEntity<List<TransferDTO>> getTransactionHistory(@ApiIgnore @AuthenticationPrincipal AuthenticatedUserDetails userDetails) {
        List<TransferDTO> transferDTOList = transactionService.getUserTransactionHistory(userDetails.getUsername());
        return ResponseEntity.ok(transferDTOList);
    }

    @GetMapping("/{transactionReference}")
    @ApiOperation("An endpoint for getting a specific transaction through its transaction reference")
    public ResponseEntity<TransferDTO> getTransaction(@ApiIgnore @AuthenticationPrincipal AuthenticatedUserDetails userDetails, @PathVariable String transactionReference) {
        TransferDTO transferDTO = transactionService.getUserTransaction(userDetails.getUsername(), transactionReference);
        return ResponseEntity.ok(transferDTO);
    }
}

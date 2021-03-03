package com.reloadly.paypro.transactionservice.unit;

import com.reloadly.paypro.transactionservice.enums.TransactionStatus;
import com.reloadly.paypro.transactionservice.exception.BadRequestException;
import com.reloadly.paypro.transactionservice.exception.NotFoundException;
import com.reloadly.paypro.transactionservice.exception.UnauthorisedAccessException;
import com.reloadly.paypro.transactionservice.payload.request.TransferRequest;
import com.reloadly.paypro.transactionservice.persistence.model.Transaction;
import com.reloadly.paypro.transactionservice.persistence.model.User;
import com.reloadly.paypro.transactionservice.persistence.repository.TransactionRepository;
import com.reloadly.paypro.transactionservice.persistence.repository.UserRepository;
import com.reloadly.paypro.transactionservice.service.impl.TransactionServiceImpl;
import com.reloadly.paypro.transactionservice.utils.TestModels;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TransactionServiceImpl transactionService;


    @Test
    void shouldThrowErrorWhenAttemptingToTransferFundsThroughAnAccountThatDoesNotExist() {
        String username = "doctorvee";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        TransferRequest transferRequest = TestModels.createTransferRequestWithoutAccountNumber();
        assertThrows(UnauthorisedAccessException.class, () -> {
            transactionService.processFundTransfer(username, transferRequest);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToTransferFundsWithoutProvidingAccountNumber() {
        String username = "doctorvee";
        given(userRepository.findByUsername(username)).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        TransferRequest transferRequest = TestModels.createTransferRequestWithoutAccountNumber();
        assertThrows(BadRequestException.class, () -> {
            transactionService.processFundTransfer(username, transferRequest);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToTransferFundsWithoutProvidingAValidAmount() {
        String username = "doctorvee";
        given(userRepository.findByUsername(username)).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        TransferRequest transferRequest = TestModels.createTransferRequestWithInvalidAmount();
        assertThrows(BadRequestException.class, () -> {
            transactionService.processFundTransfer(username, transferRequest);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToTransferFundsWithoutInsufficientBalance() {
        String username = "doctorvee";
        given(userRepository.findByUsername(username)).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        TransferRequest transferRequest = TestModels.createTransferRequest();
        transferRequest.setAmount(10001);
        assertThrows(BadRequestException.class, () -> {
            transactionService.processFundTransfer(username, transferRequest);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToTransferFundsToANonExistingAccount() {
        String username = "doctorvee";
        given(userRepository.findByUsername(username)).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        TransferRequest transferRequest = TestModels.createTransferRequest();
        given(userRepository.findByAccountNumber(transferRequest.getAccountNumber())).willReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            transactionService.processFundTransfer(username, transferRequest);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToTransferFundsToTheSameAccount() {
        String username = "doctorvee";
        given(userRepository.findByUsername(username)).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        TransferRequest transferRequest = TestModels.createTransferRequest();
        given(userRepository.findByAccountNumber(transferRequest.getAccountNumber())).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        assertThrows(BadRequestException.class, () -> {
            transactionService.processFundTransfer(username, transferRequest);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToGetTransactionHistoryThroughAnAccountThatDoesNotExist() {
        String username = "doctorvee";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        assertThrows(UnauthorisedAccessException.class, () -> {
            transactionService.getUserTransactionHistory(username);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToGetASpecificTransactionThroughAnAccountThatDoesNotExist() {
        String username = "doctorvee";
        String reference = "PP210302013513867771";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        assertThrows(UnauthorisedAccessException.class, () -> {
            transactionService.getUserTransaction(username, reference);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToGetASpecificTransactionUsingAnInvalidTransactionReference() {
        String username = "doctorvee";
        String reference = "PP210302013513867771";
        given(userRepository.findByUsername(username)).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        given(transactionRepository.findByTransactionReference(reference)).willReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            transactionService.getUserTransaction(username, reference);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowErrorWhenAttemptingToGetASpecificTransactionUsingATransactionReferenceBelongingToAnotherUser() {
        String username = "doctorvee";
        String reference = "PP210302013513867771";
        User receiver = TestModels.createDoctorVeeUser();
        User sender = TestModels.createVictorUser();
        Transaction transaction = new Transaction(sender, receiver, BigDecimal.valueOf(345), "Friendly Payment", TransactionStatus.SUCCESSFUL, reference);
        given(userRepository.findByUsername(username)).willReturn(Optional.of(TestModels.createDoctorVeeUser()));
        given(transactionRepository.findByTransactionReference(reference)).willReturn(Optional.of(transaction));
        assertThrows(UnauthorisedAccessException.class, () -> {
            transactionService.getUserTransaction(username, reference);
        });
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}

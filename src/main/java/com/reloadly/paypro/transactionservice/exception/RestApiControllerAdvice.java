package com.reloadly.paypro.transactionservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = {"com.reloadly.paypro.transactionservice.controllers"})
public class RestApiControllerAdvice {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity handleBadRequestException(BadRequestException exception){
        log.info("Exception: {}", exception.getStackTrace());
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnauthorisedAccessException.class)
    public ResponseEntity handleUnauthorisedAccessException(UnauthorisedAccessException exception){
        log.info("Exception: {}", exception.getStackTrace());
        return new ResponseEntity(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException exception){
        log.info("Exception: {}", exception.getStackTrace());
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

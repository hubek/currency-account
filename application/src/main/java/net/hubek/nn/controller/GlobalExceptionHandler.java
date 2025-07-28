package net.hubek.nn.controller;

import lombok.extern.slf4j.Slf4j;
import net.hubek.nn.controller.response.ErrorResponse;
import net.hubek.nn.currencyaccount.exception.AccountCreationException;
import net.hubek.nn.currencyaccount.exception.AccountNotFoundException;
import net.hubek.nn.currencyaccount.exception.CurrencyExchangeException;
import net.hubek.nn.currencyaccount.exchangerate.ExchangeRateProviderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            CurrencyExchangeException.class,
            AccountCreationException.class
    })
    public ResponseEntity<ErrorResponse> handle400Exception(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<ErrorResponse> handle404Exception(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ExchangeRateProviderException.class})
    public ResponseEntity<ErrorResponse> handle500Exception(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
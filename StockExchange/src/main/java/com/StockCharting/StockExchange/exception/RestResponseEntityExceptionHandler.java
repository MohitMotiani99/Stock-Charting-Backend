package com.StockCharting.StockExchange.exception;

import com.StockCharting.StockExchange.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StockExchangeNotFoundException.class)
    public ResponseEntity<ErrorMessage> stockExchangeNotFoundExceptionHandler(StockExchangeNotFoundException exception, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorMessage);
    }
    @ExceptionHandler(StockExchangeAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> stockExchangeAlreadyExistsExceptionHandler(StockExchangeAlreadyExistsException exception, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT,exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorMessage);
    }
}

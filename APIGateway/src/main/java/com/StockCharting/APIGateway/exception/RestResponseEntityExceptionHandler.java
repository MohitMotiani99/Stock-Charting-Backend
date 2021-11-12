package com.StockCharting.APIGateway.exception;

import com.StockCharting.APIGateway.dto.ErrorMessage;
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundExceptionHandler(UserNotFoundException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage()));
    }

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<ErrorMessage> notLoggedInExceptionHandler(NotLoggedInException exception, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage()));
    }

}

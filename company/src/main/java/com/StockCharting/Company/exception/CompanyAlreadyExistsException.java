package com.StockCharting.Company.exception;

public class CompanyAlreadyExistsException extends Exception{
    public CompanyAlreadyExistsException() {
        super();
    }

    public CompanyAlreadyExistsException(String message) {
        super(message);
    }

    public CompanyAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompanyAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected CompanyAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

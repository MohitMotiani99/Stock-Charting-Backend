package com.StockCharting.IPO.exception;

public class IpoNotFoundException extends Exception{

    public IpoNotFoundException() {
        super();
    }

    public IpoNotFoundException(String message) {
        super(message);
    }

    public IpoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IpoNotFoundException(Throwable cause) {
        super(cause);
    }

    protected IpoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

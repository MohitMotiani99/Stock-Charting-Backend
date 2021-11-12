package com.StockCharting.Company.exception;

public class StockExchangeNotFoundException extends Exception{
    public StockExchangeNotFoundException() {
        super();
    }

    public StockExchangeNotFoundException(String message) {
        super(message);
    }

    public StockExchangeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockExchangeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected StockExchangeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

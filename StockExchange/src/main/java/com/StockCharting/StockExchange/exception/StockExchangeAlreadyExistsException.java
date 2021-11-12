package com.StockCharting.StockExchange.exception;

public class StockExchangeAlreadyExistsException extends Exception{
    public StockExchangeAlreadyExistsException() {
        super();
    }

    public StockExchangeAlreadyExistsException(String message) {
        super(message);
    }

    public StockExchangeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public StockExchangeAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected StockExchangeAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

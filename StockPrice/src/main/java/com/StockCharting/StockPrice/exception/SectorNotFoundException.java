package com.StockCharting.StockPrice.exception;

public class SectorNotFoundException extends Exception{

    public SectorNotFoundException() {
        super();
    }

    public SectorNotFoundException(String message) {
        super(message);
    }

    public SectorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SectorNotFoundException(Throwable cause) {
        super(cause);
    }

    protected SectorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

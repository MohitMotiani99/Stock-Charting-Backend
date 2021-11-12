package com.StockCharting.Sector.exception;

public class SectorAlreadyExistsException extends Exception{

    public SectorAlreadyExistsException() {
        super();
    }

    public SectorAlreadyExistsException(String message) {
        super(message);
    }

    public SectorAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SectorAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected SectorAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.ayakovlev.interviewprep.service.exception;

public class InvalidOrderNumberException extends Exception{
    private final long maxAllowed;

    public InvalidOrderNumberException(long maxAllowed){
        super("Order number must be between 1 and " + maxAllowed);
        this.maxAllowed = maxAllowed;
    }

    public long getMaxAllowed() {
        return maxAllowed;
    }
}

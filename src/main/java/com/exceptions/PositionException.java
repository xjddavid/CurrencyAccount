package com.exceptions;

public class PositionException extends RuntimeException {
    public PositionException(String currencyCode) {
        super("Operation is not valid because the amount of " + currencyCode + " become negative");
    }
}

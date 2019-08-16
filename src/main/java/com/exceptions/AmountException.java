package com.exceptions;

public class AmountException extends RuntimeException {
    public AmountException(String amountString) {
        super(amountString + " is not a valid amount.");
    }
}

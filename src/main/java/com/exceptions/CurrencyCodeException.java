package com.exceptions;

public class CurrencyCodeException extends RuntimeException {
    public CurrencyCodeException(String currencyCode) {
        super(currencyCode + " is not a valid code. Currency code should be 3 uppercase letters.");
    }
}

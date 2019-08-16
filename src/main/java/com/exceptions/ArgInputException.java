package com.exceptions;

public class ArgInputException extends RuntimeException {
    public ArgInputException() {
        super("Arg input should be file name, with only one word.");
    }
}

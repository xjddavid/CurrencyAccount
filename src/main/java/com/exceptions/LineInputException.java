package com.exceptions;

public class LineInputException extends RuntimeException {
    public LineInputException() {
        super("Invalid line input. One good example of input is 'CNY 100'");
    }
}

package com.kata05.bloomfilter.exceptions;

public class BloomFilterException extends RuntimeException{

    private final String errorCode;

    BloomFilterException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}

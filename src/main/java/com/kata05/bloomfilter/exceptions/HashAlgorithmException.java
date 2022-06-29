package com.kata05.bloomfilter.exceptions;

public class HashAlgorithmException extends BloomFilterException {

    public HashAlgorithmException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public HashAlgorithmException(String errorMessage) {
        super("1111", errorMessage);
    }
}

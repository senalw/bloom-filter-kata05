package com.kata05.bloomfilter.enums;

public enum HashAlgorithm {
    MD5("MD5"),
    SHA256("SHA-256"),
    SHA512("SHA-512");

    private final String text;

    HashAlgorithm(final String text){
        this.text  = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

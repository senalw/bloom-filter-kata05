package com.kata05.bloomfilter.hashfunctions;

import com.kata05.bloomfilter.enums.HashAlgorithm;
import com.kata05.bloomfilter.exceptions.HashAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {

    private HashFunction() {
    }

    /**
     * Generates integer value based on the passed hashing algorithm. Allowed algorithms are MD5, SHA256 and SHA512
     *
     * @param value         {@link String value}
     * @param hashAlgorithm {@link HashAlgorithm} MD5, SHA256 or SHA512
     * @return {@link Integer} value
     */
    public static int getDigestHashFunction(@NotNull String value, HashAlgorithm hashAlgorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashAlgorithm.toString());
            byte[] digest = messageDigest.digest(value.getBytes(StandardCharsets.UTF_8));
            return Integer.parseInt(new String(Hex.encodeHex(digest)).substring(5, 10), 16);

        } catch (NoSuchAlgorithmException e) {
            throw new HashAlgorithmException(String.format("Invalid Hash function : %s", e.getMessage()));
        }
    }

}

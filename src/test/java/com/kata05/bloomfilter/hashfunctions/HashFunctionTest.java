package com.kata05.bloomfilter.hashfunctions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static com.kata05.bloomfilter.enums.HashAlgorithm.MD5;
import static com.kata05.bloomfilter.enums.HashAlgorithm.SHA256;
import static com.kata05.bloomfilter.enums.HashAlgorithm.SHA512;

class HashFunctionTest {

    @Test
    void hashAlgorithmTest(){
        // Given
        String value = "Vorvan";

        // When
        int md5sumHash = HashFunction.getDigestHashFunction(value, MD5);
        int sha256Hash = HashFunction.getDigestHashFunction(value, SHA256);
        int sha512Hash = HashFunction.getDigestHashFunction(value, SHA512);

        // Then
        assertNotEquals(md5sumHash, sha256Hash);
        assertNotEquals(md5sumHash, sha512Hash);
        assertNotEquals(sha256Hash, sha512Hash);
    }

}

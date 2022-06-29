package com.kata05.bloomfilter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

import com.kata05.bloomfilter.hashfunctions.HashFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.kata05.bloomfilter.enums.HashAlgorithm.MD5;
import static com.kata05.bloomfilter.enums.HashAlgorithm.SHA256;
import static com.kata05.bloomfilter.enums.HashAlgorithm.SHA512;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BloomFilterTest {

    private BloomFilter<String> bloomFilter;
    private List<ToIntFunction<String>> hashFunctions;

    @BeforeEach
    void beforeEach() {
        // Given
        hashFunctions = new ArrayList<>();
        hashFunctions.add(Object::hashCode);

        // why "Integer.MAX_VALUE - 8" : <a href=https://stackoverflow.com/a/64432449/4249637> Reference </a>
        bloomFilter = new BloomFilter(Integer.MAX_VALUE - 8, hashFunctions);
    }

    @Test
    void addEmptyStringExceptionThrownTest() {
        // When
        final var exception = assertThrows(IllegalArgumentException.class, () -> bloomFilter.add(""));

        // Then
        assertEquals("Empty String : unable to compute hash", exception.getMessage());
    }

    @Test
    void hasContainThrowExceptionForEmptyValuesTest() {
        // When
        final var exception = assertThrows(IllegalArgumentException.class, () ->
                bloomFilter.hasContain(""));

        // Then
        assertEquals("Empty String : unable to compute hash", exception.getMessage());
    }

    @Test
    void bloomFilterConstructorExceptionWhenBitArraySizeIsLessThanOneTest() {
        // When
        int bitArraySize = 1;
        final var exception = assertThrows(IllegalArgumentException.class, () ->
                new BloomFilter(bitArraySize, hashFunctions));

        // Then
        assertEquals(String.format("Bit array size is not enough : size %d", bitArraySize), exception.getMessage());
    }

    @Test
    void hasContainReturnsTrueForCollisionWithJavaHashCodeTest() {
        //evaluated only by hashcode hash function added in beforeEach

        // When
        bloomFilter.add("AaAa");

        // Then
        assertTrue(bloomFilter.hasContain("BBAa"));
    }

    @Test
    void hasContainReturnsFalseWhenNonCollisionForMultipleHashFunctionsTest() {
        // adding additional hash function other than the hashcode hash function
        // Given
        hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, MD5)));

        // When
        bloomFilter.add("AaAa");

        // Then
        assertFalse(bloomFilter.hasContain("BBAa"));
    }

    @Test
    void hasContainReturnsFalseWhenPassedValueIsNotAddedTest() {
        assertFalse(bloomFilter.hasContain("a"));
    }

    @Test
    void hasContainReturnsTrueWhenPassedValueIsAddedTest() {
        // When
        bloomFilter.add("AA");

        // Then
        assertTrue(bloomFilter.hasContain("AA"));
    }

    @Test
    void hasContainReturnTrueWhenBitArraySizeIsTooSmallTest() {
        // adding additional hash functions other than the hashcode hash function
        // Given
        hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, MD5)));
        hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, SHA256)));
        hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, SHA512)));

        // bit array size is too small to hold input values for each hash functions.
        bloomFilter = new BloomFilter<>(8, hashFunctions);

        // When
        bloomFilter.add("AA");
        bloomFilter.add("BB");
        bloomFilter.add("CC");
        bloomFilter.add("DD");
        bloomFilter.add("EE");

        // Then
        assertTrue(bloomFilter.hasContain("XX"));
    }

    @Test
    void wordListTest() throws URISyntaxException, IOException {
        // Given
        hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, MD5)));
        hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, SHA256)));
        hashFunctions.add((x -> HashFunction.getDigestHashFunction(x, SHA512)));

        // When
        try (var lines = Files.lines(Paths.get(Objects.requireNonNull(getClass().
                getResource("/wordlist.txt")).toURI()))) {
            lines.forEach(word -> {
                bloomFilter.add(word);
                assertTrue(bloomFilter.hasContain(word));
            });
        }

        // Then

        // Words in the dictionary
        assertTrue(bloomFilter.hasContain("hyper"));
        assertTrue(bloomFilter.hasContain("nothing"));
        assertTrue(bloomFilter.hasContain("pretty"));

        // Words not in the dictionary
        assertFalse(bloomFilter.hasContain("sodnusa"));
        assertFalse(bloomFilter.hasContain("dimalmdjf"));
        assertFalse(bloomFilter.hasContain("shoudnotbeinthedictionary"));
        assertFalse(bloomFilter.hasContain("senal"));

    }
}

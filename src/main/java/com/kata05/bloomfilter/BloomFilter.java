package com.kata05.bloomfilter;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * @param <T>
 */
public class BloomFilter<T> implements IBloomFilter<T> {

    private final boolean[] array;
    private final List<ToIntFunction<T>> hashFunctions;

    public BloomFilter(int bitArraySize, List<ToIntFunction<T>> hashFunctions) {
        if (bitArraySize <= 1) {
            throw new IllegalArgumentException(String.format("Bit array size is not enough : size %d", bitArraySize));
        }
        this.array = new boolean[bitArraySize];
        this.hashFunctions = hashFunctions;
    }

    @Override
    public void add(@NotNull T value) {
        if (value instanceof String && ((String) value).isEmpty()) {
            throw new IllegalArgumentException("Empty String : unable to compute hash");
        }

        for (var function : hashFunctions) {
            // if <T> type is not a String then, it's required to have a String conversion before it's being
            // passed to the hash generation function.
            int hash = findIndexForHash(function.applyAsInt((T) String.valueOf(value)));
            array[hash] = true;
        }
    }

    @Override
    public boolean hasContain(@NotNull T value) {
        if (value instanceof String && ((String) value).isEmpty()) {
            throw new IllegalArgumentException("Empty String : unable to compute hash");
        }

        for (var function : hashFunctions) {
            // if <T> type is not a String then, it's required to have a String conversion before it's being
            // passed to the hash generation function.
            int hash = findIndexForHash(function.applyAsInt((T) String.valueOf(value)));
            if (!array[hash]) {
                return false;
            }
        }
        return true;
    }

    private int findIndexForHash(int hash) {
        return hash & (array.length - 1); // find the index of bit array
    }

}

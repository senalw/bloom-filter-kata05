package com.kata05.bloomfilter;

/**
 * This bloom filter implementation allows to have multiple hash functions and also
 * configurable bit array size at the same time.
 *
 * @param <T> Type of the BloomFilter.
 *            E.g. BloomFilter<String> bf = new BloomFilter(Integer.MAX_VALUE - 8, hashFunctions);
 *            BloomFilter<Integer> bf = new BloomFilter(Integer.MAX_VALUE - 8, hashFunctions);
 */
public interface IBloomFilter<T> {

    /**
     * Adding values to the bit array
     *
     * @param value {@link T value}
     */
    void add(T value);

    /**
     * Check whether the pass {@link T value} is available in the bit array.
     *
     * @param value {@link T value}
     * @return {@link Boolean} status of the availability of passed value
     */
    boolean hasContain(T value);

}

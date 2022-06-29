package com.kata05.bloomfilter;

public interface IBloomFilter<T> {

    void add(T value);

    boolean hasContain(T value);

}

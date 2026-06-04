package com.datastruct;

public interface Tree<K,V> {
    void insert(K key, V data);
    void delete(K key);
}
package com.code.file.upload.dsa.cache;

public interface LRU<K, V> {

    void writeToCache(K key, V value);

    V readFromCache(K key);

}

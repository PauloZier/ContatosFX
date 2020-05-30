package org.contatosapp.util;

public interface Pair <K, V> {

    void put(K key, V value);
    K getKey();
    V getValue();

}

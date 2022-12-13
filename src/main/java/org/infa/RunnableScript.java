package org.infa;

public interface RunnableScript<K,V>{
    void run(K key, V value);
}

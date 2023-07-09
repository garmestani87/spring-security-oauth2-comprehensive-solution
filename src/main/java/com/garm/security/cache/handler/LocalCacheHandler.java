package com.garm.security.cache.handler;


public interface LocalCacheHandler<T> {
    T get(String key);

    T put(String key, T object);

    T put(String key, T object, long timeout);

    void remove(String key);

}
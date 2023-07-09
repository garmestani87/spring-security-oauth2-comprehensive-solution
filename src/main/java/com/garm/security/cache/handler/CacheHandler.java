package com.garm.security.cache.handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheHandler<T> implements LocalCacheHandler<T> {

    @Override
    public T get(String key) {
        return null;
    }

    @Override
    public T put(String key, T object) {
        return null;
    }

    @Override
    public T put(String key, T object, long timeout) {
        return null;
    }

    @Override
    public void remove(String key) {

    }
}
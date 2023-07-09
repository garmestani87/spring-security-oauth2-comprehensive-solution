package com.garm.security.cache;

import com.garm.security.cache.handler.CacheHandler;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationCodeCacheService extends CacheHandler<String> {
    // implement your cache handler
}

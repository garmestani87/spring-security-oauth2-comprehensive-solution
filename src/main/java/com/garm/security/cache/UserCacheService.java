package com.garm.security.cache;

import com.garm.security.cache.handler.CacheHandler;
import com.garm.security.domain.dto.UserDetailsDto;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService extends CacheHandler<UserDetailsDto> {
    // implement your cache handler
}

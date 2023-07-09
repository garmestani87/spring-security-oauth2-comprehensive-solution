package com.garm.security.cache;

import com.garm.security.cache.handler.CacheHandler;
import com.garm.security.domain.dto.UserInfoDto;
import org.springframework.stereotype.Service;

@Service
public class UserInfoCacheService extends CacheHandler<UserInfoDto> {
    // implement your cache handler
}

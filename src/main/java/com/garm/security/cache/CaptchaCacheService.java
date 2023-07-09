package com.garm.security.cache;

import com.garm.security.cache.handler.CacheHandler;
import com.garm.security.domain.dto.CaptchaDto;
import org.springframework.stereotype.Service;

@Service
public class CaptchaCacheService extends CacheHandler<CaptchaDto> {
    // implement your cache handler
}

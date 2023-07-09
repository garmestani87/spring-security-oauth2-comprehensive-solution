package com.garm.security.service.log;

import org.springframework.web.util.ContentCachingRequestWrapper;

public interface RequestLogService {
    void logRequest(ContentCachingRequestWrapper request, String body);
}

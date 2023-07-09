package com.garm.security.filter;

import com.garm.security.service.log.impl.RequestLogServiceImpl;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

public class LoggingFilter extends AbstractRequestLoggingFilter {

    private final RequestLogServiceImpl requestLogBusiness;

    public LoggingFilter(RequestLogServiceImpl requestLogBusiness) {
        this.requestLogBusiness = requestLogBusiness;
    }

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String s) {
        System.out.println("before proxy request");
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String s) {
        byte[] contentAsByteArray = ((ContentCachingRequestWrapper) request).getContentAsByteArray();
        String body = "";
        if (ArrayUtils.isNotEmpty(contentAsByteArray)) {
            body = new String(contentAsByteArray, StandardCharsets.UTF_8);
        }
        requestLogBusiness.logRequest(new ContentCachingRequestWrapper(request), body);
    }

    @Override
    protected boolean isIncludeClientInfo() {
        return true;
    }

    @Override
    protected boolean isIncludePayload() {
        return true;
    }

    @Override
    protected boolean isIncludeQueryString() {
        return true;
    }

}

package com.garm.security.service.log.impl;

import com.garm.security.domain.document.RequestLog;
import com.garm.security.repository.log.RequestLogRepository;
import com.garm.security.service.log.RequestLogService;
import com.garm.security.util.SecurityConstant;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RequestLogServiceImpl implements RequestLogService {

    private final RequestLogRepository repository;

    @SneakyThrows
    @Override
    public void logRequest(ContentCachingRequestWrapper request, String body) {
        repository.save(RequestLog.builder()
                .token(((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenValue())
                .locale(request.getLocale().getDisplayName())
                .method(request.getMethod())
                .remoteAddress(request.getRemoteAddr())
                .remotePort(request.getRemotePort())
                .serverName(request.getServerName())
                .serverPort(request.getServerPort())
                .protocol(request.getProtocol())
                .uri(request.getRequestURI())
                .userAgent(request.getHeader(SecurityConstant.USER_AGENT))
                .parameter(body)
                .insertDate(LocalDateTime.now())
                .insertUserName(SecurityContextHolder.getContext().getAuthentication().getName())
                .build());
    }

}

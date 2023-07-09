package com.garm.security.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestLogDto {
    private Long id;
    private String locale;
    private String method;
    private String message;
    private String protocol;
    private String remoteAddress;
    private Integer remotePort;
    private String serverName;
    private Integer serverPort;
    private String uri;
    private String userAgent;
    private String parameter;
    private String token;
    private Long version;
    private String insertUserName;
    private LocalDateTime insertDate;
}
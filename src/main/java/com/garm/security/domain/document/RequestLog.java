package com.garm.security.domain.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@Document
public class RequestLog {

    @Id
    private String id;
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
    private String insertUserName;
    private Long insertOrganizationId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime insertDate;


}
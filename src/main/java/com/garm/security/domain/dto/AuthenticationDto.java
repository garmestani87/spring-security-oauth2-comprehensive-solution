package com.garm.security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {
    private String client_id;
    private String client_secret;
    private String username;
    private String password;
    private String grant_type;
    private String captcha;
    private OtpDto otp;
}

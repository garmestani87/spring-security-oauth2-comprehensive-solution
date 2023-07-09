package com.garm.security.util;

public interface SecurityConstant {

    long MAX_FAILED = 3;
    String USER_AGENT = "User-Agent";
    String USERNAME = "username";
    String PASSWORD = "password";
    String GRANT_TYPE = "grant_type";
    String AUTHORIZATION = "Authorization";
    String CAPTCHA = "captcha";
    String HAS_ERROR = "false";
    String OTP = "otp";
    String VERIFICATION_CODE = "verificationCode";
    String OTP_IDENTIFIER = "smsIdentifier";
    String TOKEN_KEY = "access-token";

    String CAPTCHA_KEY = "captcha-identifier";
    String LOGIN_URL = "/login/**";
    String LOGIN_WEB_URL = "/login/web";
    String LOGIN_REST_URL = "/login/rest";
    String LOGIN_OTP_STEP1 = "/login/otp";
    String LOGIN_OTP_STEP2 = "/login/otp/verify";
    String SECURITY_BASE_URL = "/api/security";

    interface SpecialPath {
        String LOGOUT = "/api/security/logout";
    }

    interface SpecialCharacter {
        String LEFT_BRACKET = "[";
        String RIGHT_BRACKET = "]";
        String EQUAL = "=";
        String COLON = ";";
    }
}

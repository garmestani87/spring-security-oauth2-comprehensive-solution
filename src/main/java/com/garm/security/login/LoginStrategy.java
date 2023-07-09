package com.garm.security.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garm.security.cache.CaptchaCacheService;
import com.garm.security.cache.OtpCacheService;
import com.garm.security.domain.dto.AuthenticationDto;
import com.garm.security.domain.dto.CaptchaDto;
import com.garm.security.domain.dto.OtpDto;
import com.garm.security.helper.ErrorMessageHelper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.garm.security.helper.SecurityHelper.setCharset;
import static com.garm.security.util.SecurityConstant.HAS_ERROR;

public interface LoginStrategy {

    void login(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException;

    default void validate(final ServletRequest request, final ServletResponse response, final AuthenticationDto authenticationDto, final HashMap<String, String[]> params) {
    }

    default void setParams(final HashMap<String, String[]> params) {
    }

    @SneakyThrows
    default void setResponse(final HttpServletResponse response, String body, final HashMap<String, String[]> params) {
        params.put(HAS_ERROR, new String[]{"true"});
        response.setStatus(HttpStatus.OK.value());
        setCharset(response);
        response.getWriter().write(body);
    }

    @SneakyThrows
    default void setError(final HttpServletResponse response, String message, final HashMap<String, String[]> params) {
        params.put(HAS_ERROR, new String[]{"true"});
        ErrorMessageHelper.setErrorMessage(response, message);
    }

    default void expireCaptcha(final CaptchaCacheService captchaCache, String captchaIdentifier) {
        captchaCache.put(captchaIdentifier, new CaptchaDto());
    }

    default void expireOtp(final OtpCacheService otpCacheService, String otpIdentifier) {
        otpCacheService.put(otpIdentifier, new OtpDto());
    }

    default ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

}

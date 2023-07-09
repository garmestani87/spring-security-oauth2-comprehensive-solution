package com.garm.security.filter;

import com.garm.security.cache.CaptchaCacheService;
import com.garm.security.cache.OtpCacheService;
import com.garm.security.login.OtpLoginStep1;
import com.garm.security.login.OtpLoginStep2;
import com.garm.security.login.RestLogin;
import com.garm.security.login.WebLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.garm.security.util.SecurityConstant.*;

@Component
@RequiredArgsConstructor
@Order(value = Integer.MIN_VALUE)
public class JsonToUrlEncodedAuthenticationFilter extends GenericFilterBean {

    private final CaptchaCacheService captchaCache;
    private final OtpCacheService otpCacheService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String requestURI = ((HttpServletRequest) request).getRequestURI();

        switch (((HttpServletRequest) request).getRequestURI()) {
            case LOGIN_WEB_URL:
                new WebLogin(requestURI, captchaCache).login(request, response, chain);
                break;
            case LOGIN_REST_URL:
                new RestLogin(requestURI).login(request, response, chain);
                break;
            case LOGIN_OTP_STEP1:
                new OtpLoginStep1(requestURI, captchaCache).login(request, response, chain);
                break;
            case LOGIN_OTP_STEP2:
                new OtpLoginStep2(requestURI, otpCacheService).login(request, response, chain);
                break;
            default:
                chain.doFilter(request, response);
        }

    }
}
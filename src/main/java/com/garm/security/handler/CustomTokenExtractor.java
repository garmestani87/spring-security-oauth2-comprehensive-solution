package com.garm.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static com.garm.security.util.SecurityConstant.TOKEN_KEY;


public class CustomTokenExtractor implements TokenExtractor {

    @Override
    public Authentication extract(HttpServletRequest request) {
        return new PreAuthenticatedAuthenticationToken(getTokenFromRequest(request), "");
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(TOKEN_KEY))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
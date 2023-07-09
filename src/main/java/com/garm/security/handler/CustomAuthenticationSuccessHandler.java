package com.garm.security.handler;

import com.garm.security.helper.SecurityHelper;
import com.garm.security.provider.MongoTokenStore;
import com.garm.security.wrapper.CustomServletResponseWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.garm.security.util.SecurityConstant.TOKEN_KEY;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MongoTokenStore tokenStore;

    public CustomAuthenticationSuccessHandler(MongoTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws ServletException, IOException {
        chain.doFilter(request, response);
        onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<OAuth2AccessToken> tokensByUserName = tokenStore.findTokensByClientIdAndUserName(SecurityContextHolder.getContext().getAuthentication().getName(), authentication.getName());

        if (CollectionUtils.isNotEmpty(tokensByUserName))
            response.addCookie(SecurityHelper.setCookie(TOKEN_KEY, tokensByUserName.iterator().next().getValue()));

        response.setStatus(HttpStatus.OK.value());
        response.flushBuffer();
        ((CustomServletResponseWrapper) response).flushFinal();
    }
}

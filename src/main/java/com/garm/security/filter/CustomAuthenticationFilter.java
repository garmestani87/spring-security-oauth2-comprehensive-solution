package com.garm.security.filter;

import com.garm.security.cache.CaptchaCacheService;
import com.garm.security.cache.TokenCacheService;
import com.garm.security.cache.UserCacheService;
import com.garm.security.domain.dto.CaptchaDto;
import com.garm.security.domain.dto.UserDetailsDto;
import com.garm.security.helper.TokenHelper;
import com.garm.security.provider.MongoTokenStore;
import com.garm.security.wrapper.CustomServletResponseWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static com.garm.security.util.SecurityConstant.*;


public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenCacheService tokenCache;
    private final UserCacheService userCache;
    private final MongoTokenStore tokenStore;
    private final ThreadLocal<UserDetailsDto> users;
    private final CaptchaCacheService captchaCache;
    private final Environment env;

    public CustomAuthenticationFilter(TokenCacheService tokenCache,
                                      MongoTokenStore tokenStore,
                                      UserCacheService userProfileCache,
                                      CaptchaCacheService captchaCache,
                                      Environment env) {
        super(LOGIN_URL);
        this.tokenCache = tokenCache;
        this.tokenStore = tokenStore;
        this.userCache = userProfileCache;
        this.users = new ThreadLocal<>();
        this.captchaCache = captchaCache;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);

        TokenHelper.removeTokens(Collections.singletonList(username), request.getUserPrincipal().getName());
        users.set(new UserDetailsDto().setUserName(username).setPassword(password));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain, final Authentication authResult)
            throws IOException, ServletException {

        getSuccessHandler().onAuthenticationSuccess(request, new CustomServletResponseWrapper(response, env), chain, authResult);

        captchaCache.put(request.getHeader(CAPTCHA_KEY), new CaptchaDto());
        Collection<OAuth2AccessToken> tokensByUserName = tokenStore.findTokensByClientIdAndUserName(request.getUserPrincipal().getName(), authResult.getName());
        if (CollectionUtils.isNotEmpty(tokensByUserName)) {
            tokenCache.put(authResult.getName(), tokensByUserName.iterator().next().getValue());
            userCache.put(authResult.getName(), users.get());
        }
        // send sms to your sms provider
    }
}
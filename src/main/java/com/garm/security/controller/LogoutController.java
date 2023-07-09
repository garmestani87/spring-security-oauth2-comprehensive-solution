package com.garm.security.controller;

import com.garm.security.cache.UserInfoCacheService;
import com.garm.security.domain.dto.UserInfoDto;
import com.garm.security.helper.SecurityHelper;
import com.garm.security.provider.MongoTokenStore;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class LogoutController {

    private final MongoTokenStore tokenStore;
    private final UserInfoCacheService userInfoCacheService;

    @PostMapping("logout")
    public void logOut(HttpServletResponse response, Authentication authentication) {
        Collection<OAuth2AccessToken> tokensByUserName = tokenStore.findTokensByClientIdAndUserName(SecurityHelper.getClientId(), authentication.getName());
        if (CollectionUtils.isNotEmpty(tokensByUserName)) {
            tokenStore.removeAccessToken(tokenStore.readAccessToken(tokensByUserName.iterator().next().getValue()));
            tokenStore.removeRefreshToken(tokensByUserName.iterator().next().getRefreshToken());
            userInfoCacheService.put(authentication.getName(), new UserInfoDto());
        }
    }

}

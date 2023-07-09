package com.garm.security.helper;

import com.garm.security.provider.MongoTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.garm.security.helper.SecurityHelper.clearUserInfoCache;

@Component
public class TokenHelper {

    private static MongoTokenStore mongoTokenStore;

    @Autowired
    @Qualifier("MongoTokenStore")
    private MongoTokenStore tokenStore;

    @PostConstruct
    public void init() {
        mongoTokenStore = this.tokenStore;
    }

    public static void removeTokens(List<String> usernameList, String clientId) {
        usernameList.forEach(username -> {
            clearUserInfoCache(username);
            for (OAuth2AccessToken oAuth2AccessToken : mongoTokenStore.findTokensByClientIdAndUserName(username, clientId)) {
                mongoTokenStore.removeAccessToken(oAuth2AccessToken);
                mongoTokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
            }
        });
    }

}

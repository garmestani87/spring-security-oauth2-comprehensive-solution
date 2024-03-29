package com.garm.security.repository.mongo;


import com.garm.security.domain.document.MongoOAuth2AccessToken;

import java.util.List;

public interface MongoOAuth2AccessTokenRepositoryBase {
    MongoOAuth2AccessToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);

    boolean deleteByRefreshTokenId(String refreshTokenId);

    MongoOAuth2AccessToken findByAuthenticationId(String key);

    List<MongoOAuth2AccessToken> findByUsernameAndClientId(String username, String clientId);

    List<MongoOAuth2AccessToken> findByClientId(String clientId);
}

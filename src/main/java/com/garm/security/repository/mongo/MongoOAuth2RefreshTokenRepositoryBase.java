package com.garm.security.repository.mongo;


import com.garm.security.domain.document.MongoOAuth2RefreshToken;

public interface MongoOAuth2RefreshTokenRepositoryBase {
    MongoOAuth2RefreshToken findByTokenId(String tokenId);

    boolean deleteByTokenId(String tokenId);
}

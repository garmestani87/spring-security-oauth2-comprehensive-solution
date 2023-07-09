package com.garm.security.repository.mongo;


import com.garm.security.domain.document.MongoOAuth2RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoOAuth2RefreshTokenRepository extends MongoRepository<MongoOAuth2RefreshToken, String>, MongoOAuth2RefreshTokenRepositoryBase {
}

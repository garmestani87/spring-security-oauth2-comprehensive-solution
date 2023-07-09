package com.garm.security.repository.mongo;

import com.garm.security.domain.document.MongoOAuth2AccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoOAuth2AccessTokenRepository extends MongoRepository<MongoOAuth2AccessToken, String>, MongoOAuth2AccessTokenRepositoryBase {

}

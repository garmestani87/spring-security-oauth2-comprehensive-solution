package com.garm.security.repository.mongo;

import com.garm.security.domain.document.MongoOAuth2ClientToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoOAuth2ClientTokenRepository extends MongoRepository<MongoOAuth2ClientToken, String>, MongoOAuth2ClientTokenRepositoryBase {
}

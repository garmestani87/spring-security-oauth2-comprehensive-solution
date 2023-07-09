package com.garm.security.repository.mongo;

import com.garm.security.domain.document.MongoClientDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoClientDetailsRepository extends MongoRepository<MongoClientDetails, String>, MongoClientDetailsRepositoryBase {
}

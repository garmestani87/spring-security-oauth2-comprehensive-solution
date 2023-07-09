package com.garm.security.repository.mongo;

import com.garm.security.domain.document.MongoApproval;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoApprovalRepository extends MongoRepository<MongoApproval, String>, MongoApprovalRepositoryBase {
}

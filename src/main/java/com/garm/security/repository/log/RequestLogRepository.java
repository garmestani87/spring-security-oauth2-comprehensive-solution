package com.garm.security.repository.log;

import com.garm.security.domain.document.RequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends MongoRepository<RequestLog, Long> {
}
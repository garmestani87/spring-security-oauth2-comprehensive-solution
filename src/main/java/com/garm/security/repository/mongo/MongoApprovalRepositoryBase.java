package com.garm.security.repository.mongo;


import com.garm.security.domain.document.MongoApproval;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MongoApprovalRepositoryBase {
    boolean updateOrCreate(Collection<MongoApproval> mongoApprovals);

    boolean updateExpiresAt(LocalDateTime now, MongoApproval mongoApproval);

    boolean deleteByUserIdAndClientIdAndScope(MongoApproval mongoApproval);

    List<MongoApproval> findByUserIdAndClientId(String userId, String clientId);
}

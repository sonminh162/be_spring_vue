package com.rabiloo.custom.repository;

import com.rabiloo.base.core.BaseRepository;
import com.rabiloo.custom.entity.GroupPolicyParticipantEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupPolicyRepository extends BaseRepository<GroupPolicyParticipantEntity> {
    List<GroupPolicyParticipantEntity> findByGroupIdInAndIsDeletedFalse(List<Long> groupIds);
    List<GroupPolicyParticipantEntity> findByGroupIdAndIsDeletedFalse(Long policyId);
}

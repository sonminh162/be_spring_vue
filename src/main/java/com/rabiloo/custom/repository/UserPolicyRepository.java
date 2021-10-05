package com.rabiloo.custom.repository;

import com.rabiloo.base.core.BaseRepository;
import com.rabiloo.custom.entity.UserPolicyParticipantEntity;

import java.util.List;

public interface UserPolicyRepository extends BaseRepository<UserPolicyParticipantEntity> {

    List<UserPolicyParticipantEntity> findAllByUserIdAndIsDeletedFalse(Long userId);
    List<UserPolicyParticipantEntity> findByPermissionIdAndIsDeletedFalse(Long permissionId);
}

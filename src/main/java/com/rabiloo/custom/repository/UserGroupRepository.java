package com.rabiloo.custom.repository;

import com.rabiloo.base.core.BaseRepository;
import com.rabiloo.custom.entity.UserGroupParticipantEntity;

import java.util.List;

public interface UserGroupRepository extends BaseRepository<UserGroupParticipantEntity> {
    List<UserGroupParticipantEntity> findAllByUserIdAndIsDeletedFalse(Long userId);
}

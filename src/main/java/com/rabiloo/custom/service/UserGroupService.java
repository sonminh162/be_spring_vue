package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.UserGroupParticipantEntity;
import com.rabiloo.custom.repository.UserGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupService extends BaseService<UserGroupParticipantEntity, UserGroupRepository> {
    public boolean deleteParticipantsByUserId(Long userId) {
        List<UserGroupParticipantEntity> deletingEntities = repository.findAllByUserIdAndIsDeletedFalse(userId);
        return deleteList(deletingEntities);
    }

    public List<UserGroupParticipantEntity> findAllByGroupId(Long groupId) {
        return repository.findByGroupIdAndIsDeletedFalse(groupId);
    }

    public boolean deleteParticipantsByGroupId(Long groupId) {
        List<UserGroupParticipantEntity> deletingEntities = repository.findAllByGroupIdAndIsDeletedFalse(groupId);
        return deleteList(deletingEntities);
    }

}

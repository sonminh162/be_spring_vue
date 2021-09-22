package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.UserPolicyParticipantEntity;
import com.rabiloo.custom.repository.UserPolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPolicyService extends BaseService<UserPolicyParticipantEntity, UserPolicyRepository> {

    public UserPolicyParticipantEntity createUserPolicyParticipant(UserPolicyParticipantEntity userPolicyParticipantEntity) {
        return repository.save(userPolicyParticipantEntity);
    }

    public boolean deleteParticipantsByUserId(Long userId) {
        List<UserPolicyParticipantEntity> deletingEntities = repository.findAllByUserIdAndIsDeletedFalse(userId);
        return deleteList(deletingEntities);
    }
}

package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.UserPolicyParticipantEntity;
import com.rabiloo.custom.repository.UserPolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPolicyService extends BaseService<UserPolicyParticipantEntity, UserPolicyRepository> {

    public UserPolicyParticipantEntity createUserPolicyParticipant(UserPolicyParticipantEntity userPolicyParticipantEntity) {
        return repository.save(userPolicyParticipantEntity);
    }

    public boolean deleteParticipantsByUserId(Long userId) {
        List<UserPolicyParticipantEntity> deletingEntities = repository.findAllByUserIdAndIsDeletedFalse(userId);
        return deleteList(deletingEntities);
    }

    public List<UserPolicyParticipantEntity> findByPermissionId(Long permissionId) {
        return repository.findByPermissionIdAndIsDeletedFalse(permissionId);
    }

    public List<String> getPolicyNamesByUserId(Long userId) {
        return repository.findAllByUserIdAndIsDeletedFalse(userId)
                .stream()
                .map(UserPolicyParticipantEntity::getPermissionName).collect(Collectors.toList());
    }
}

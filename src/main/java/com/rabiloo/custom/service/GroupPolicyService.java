package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.GroupPolicyParticipantEntity;
import com.rabiloo.custom.repository.GroupPolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupPolicyService extends BaseService<GroupPolicyParticipantEntity, GroupPolicyRepository> {
    public List<GroupPolicyParticipantEntity> findAllByGroupIdInAndIsDeletedFalse(List<Long> ids) {
        return repository.findByGroupIdInAndIsDeletedFalse(ids);
    }

    public boolean deleteParticipantsByGroupId(Long id) {
        List<GroupPolicyParticipantEntity> deletingParticipants = repository.findByGroupIdAndIsDeletedFalse(id);
        return deleteList(deletingParticipants);
    }

    public List<GroupPolicyParticipantEntity> findByGroupIdAndIsDeletedFalse(Long groupId) {
        return repository.findByGroupIdAndIsDeletedFalse(groupId);
    }

    public List<GroupPolicyParticipantEntity> findByPermissionId(Long permissionId) {
        return repository.findByPermissionIdAndIsDeletedFalse(permissionId);
    }

}

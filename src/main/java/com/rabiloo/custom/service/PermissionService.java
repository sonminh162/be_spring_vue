package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.PermissionPolicyEntity;
import com.rabiloo.custom.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PermissionService extends BaseService<PermissionPolicyEntity, PermissionRepository> {

    public List<PermissionPolicyEntity> findAllByUserId(Long userId) {
        return repository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    public PermissionPolicyEntity createPermissionAndModifyParticipants(PermissionPolicyEntity permissionPolicyEntity,
                                                                        List<Long> userIds,
                                                                        List<Long> groupIds) {
        PermissionPolicyEntity creatingPermission = save(permissionPolicyEntity);
        if(userIds != null) {
            //assign new permission for specific user
        }
        if(groupIds != null) {
            //assign new permission for specific group
        }
        return creatingPermission;
    }

    public PermissionPolicyEntity updatePermissionAndModifyParticipants(UUID code,
                                                                        PermissionPolicyEntity requestBody,
                                                                        List<Long> userIds,
                                                                        List<Long> groupIds) {
        PermissionPolicyEntity updatingPermission = findByCodeAndIsDeleteFalse(code);
        updatePermission(updatingPermission, requestBody);
        if(userIds != null) {

        }
        if(groupIds != null) {

        }
        return updatingPermission;
    }

    private void updatePermission(PermissionPolicyEntity updatingPermission, PermissionPolicyEntity requestBody) {

    }

    public boolean deletePermissionPolicyAndModifyParticipants(UUID permissionCode) {
        return true;
    }
}

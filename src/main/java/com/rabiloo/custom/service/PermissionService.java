package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.dto.permission.PermissionPolicyDto;
import com.rabiloo.custom.entity.GroupPolicyParticipantEntity;
import com.rabiloo.custom.entity.permission.PermissionPolicyEntity;
import com.rabiloo.custom.entity.UserPolicyParticipantEntity;
import com.rabiloo.custom.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PermissionService extends BaseService<PermissionPolicyEntity, PermissionRepository> {

    @Autowired
    UserPolicyService userPolicyService;

    @Autowired
    GroupPolicyService groupPolicyService;

    public List<PermissionPolicyEntity> findAllByUserId(Long userId) {
        return repository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    public PermissionPolicyEntity createPermissionAndModifyParticipants(PermissionPolicyEntity permissionPolicyEntity,
                                                                        List<Long> userIds,
                                                                        List<Long> groupIds) {
        PermissionPolicyEntity createdPermission = save(permissionPolicyEntity);
        if(userIds != null) {
            assignNewPermissionForUser(userIds, createdPermission.getId());
        }
        if(groupIds != null) {
            assignNewPermissionForGroup(groupIds, createdPermission.getId());
        }
        return createdPermission;
    }

    private void assignNewPermissionForGroup(List<Long> groupIds, Long permissionId) {
        List<GroupPolicyParticipantEntity> newGroupPermissions = new ArrayList<>();
        for(Long groupId : groupIds) {
            newGroupPermissions.add(new GroupPolicyParticipantEntity(groupId, permissionId));
        }
        groupPolicyService.saveList(newGroupPermissions);
    }

    private void assignNewPermissionForUser(List<Long> userIds, Long permissionId) {
        List<UserPolicyParticipantEntity> newUserPermissions = new ArrayList<>();
        for(Long userId : userIds) {
            newUserPermissions.add(new UserPolicyParticipantEntity(userId, permissionId));
        }
        userPolicyService.saveList(newUserPermissions);
    }

    public PermissionPolicyEntity updatePermissionAndModifyParticipants(UUID code,
                                                                        PermissionPolicyEntity requestBody,
                                                                        List<Long> userIds,
                                                                        List<Long> groupIds) {
        PermissionPolicyEntity updatingPermission = findByCodeAndIsDeleteFalse(code);
        updatePermission(updatingPermission, requestBody);

        userIds = getUpdatingUserIdsByFilterSavedThings(userIds, updatingPermission.getId());
        groupIds = getUpdatingGroupIdsByFilterSavedThings(groupIds, updatingPermission.getId());

        if(userIds.size() > 0) {
            List<UserPolicyParticipantEntity> listToSave = new ArrayList<>();
            for(Long userId : userIds) {
                listToSave.add(new UserPolicyParticipantEntity(userId, updatingPermission.getId()));
            }
            userPolicyService.saveList(listToSave);
        }
        if(groupIds.size() > 0) {
            List<GroupPolicyParticipantEntity> listToSave = new ArrayList<>();
            for(Long groupId : groupIds) {
                listToSave.add(new GroupPolicyParticipantEntity(groupId, updatingPermission.getId()));
            }
            groupPolicyService.saveList(listToSave);
        }
        return updatingPermission;
    }

    private List<Long> getUpdatingUserIdsByFilterSavedThings(List<Long> userIds, Long permissionId) {
        List<Long> updatingUserIds = new ArrayList<>();
        List<Long> savedUserIdsByPermissionId = userPolicyService.findByPermissionId(permissionId)
                .stream()
                .map(UserPolicyParticipantEntity::getUserId).collect(Collectors.toList());
        for(Long updatingUserId : userIds) {
            if(!savedUserIdsByPermissionId.contains(updatingUserId)) {
                updatingUserIds.add(updatingUserId);
            }
        }
        return updatingUserIds;
    }

    private List<Long> getUpdatingGroupIdsByFilterSavedThings(List<Long> groupIds, Long permissionId) {
        List<Long> updatingGroupIds = new ArrayList<>();
        List<Long> savedGroupIdsByPermissionId = groupPolicyService.findByPermissionId(permissionId)
                .stream()
                .map(GroupPolicyParticipantEntity::getGroupId).collect(Collectors.toList());
        for(Long groupId : groupIds) {
            if(!savedGroupIdsByPermissionId.contains(groupId)) {
                updatingGroupIds.add(groupId);
            }
        }
        return updatingGroupIds;
    }

    private void updatePermission(PermissionPolicyEntity updatingPermission, PermissionPolicyEntity requestBody) {
        updatingPermission.setAction(requestBody.getAction());
        updatingPermission.setDescription(requestBody.getDescription());
        updatingPermission.setName(requestBody.getName());
        updatingPermission.setResourceType(requestBody.getResourceType());
        updatingPermission.setResourceId(requestBody.getResourceId());
    }

    @Transactional
    public boolean deletePermissionPolicyAndModifyParticipants(UUID permissionCode) {
        // cau hoi: luong chuan cua delete cac thu co can doi hoan thanh lenh hay khong
        PermissionPolicyEntity deletingPermission = findByCodeAndIsDeleteFalse(permissionCode);
        deleteEntity(deletingPermission);

        List<UserPolicyParticipantEntity> userPolicyParticipants = userPolicyService.findByPermissionId(deletingPermission.getId());
        userPolicyService.deleteList(userPolicyParticipants);

        List<GroupPolicyParticipantEntity> groupPolicyParticipantEntities = groupPolicyService.findByPermissionId(deletingPermission.getId());
        groupPolicyService.deleteList(groupPolicyParticipantEntities);
        return true;
    }

    public List<PermissionPolicyDto> getList() {
        return findByIsDeletedFalse()
                .stream()
                .map(PermissionPolicyDto::new).collect(Collectors.toList());
    }

    private List<PermissionPolicyEntity> findByIsDeletedFalse() {
        return repository.findByIsDeletedFalse();
    }
}

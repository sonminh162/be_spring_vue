package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.dto.group.GroupDto;
import com.rabiloo.custom.entity.*;
import com.rabiloo.custom.entity.permission.PermissionPolicyEntity;
import com.rabiloo.custom.entity.user.UserEntity;
import com.rabiloo.custom.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService extends BaseService<GroupEntity, GroupRepository> {

    @Autowired
    UserService userService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    GroupPolicyService groupPolicyService;

    @Autowired
    UserPolicyService userPolicyService;

    @Autowired
    PermissionService permissionService;

    private void addUsersToNewGroup(List<Long> userIds, GroupEntity groupEntity) {
        List<UserEntity> assignedUsers = userService.findByIdInAndDeletedFalse(userIds);
        if(assignedUsers != null && !assignedUsers.isEmpty()) {
            for(UserEntity userEntity : assignedUsers) {
                userGroupService.save(new UserGroupParticipantEntity(userEntity.getId(), groupEntity.getId(), userEntity.getUsername(), groupEntity.getName()));
            }
        }
    }

    private void addPermissionsToGroup(List<Long> permissionIds, GroupEntity groupEntity) {
        for(Long permissionId : permissionIds) {
            groupPolicyService.save(new GroupPolicyParticipantEntity(groupEntity.getId(), permissionId));
        }

        List<UserEntity> usersInGroup = userService.findByGroupId(groupEntity.getId());
        updateUserPermission(usersInGroup, permissionIds);

    }

    private void updateUserPermission(List<UserEntity> usersInGroup, List<Long> permissionIds) {
        for(Long permissionId : permissionIds) {
            for(UserEntity userEntity : usersInGroup) {
                userPolicyService.createUserPolicyParticipant(new UserPolicyParticipantEntity(userEntity.getId(), permissionId));
            }
        }
    }

    public GroupEntity createAndModifyParticipants(GroupEntity requestBody, List<Long> userIds, List<Long> permissionIds) {
        GroupEntity createdGroup = save(requestBody);
        if(createdGroup != null) {
            if(userIds != null) {
                addUsersToNewGroup(userIds, createdGroup);
            }
            if(permissionIds != null) {
                addPermissionsToGroup(permissionIds, createdGroup);
            }
            return createdGroup;
        }
        return null;
    }

    private GroupEntity getUpdatedAttributesGroup(GroupDto dto, GroupEntity updatingGroup) {
        updatingGroup.setName(dto.getName());
        updatingGroup.setDescription(dto.getDescription());
        return updatingGroup;
    }

    private void assignPermissionOfGroupToNewUsers(List<Long> newUpdatingUserIds, Long groupId) {
        List<Long> permissionIds = groupPolicyService.findByGroupIdAndIsDeletedFalse(groupId)
                .stream().map(GroupPolicyParticipantEntity::getPolicyId).collect(Collectors.toList());
        List<UserPolicyParticipantEntity> userPolicyParticipantsToSave = new ArrayList<>();
        for(Long newUpdatingUserId : newUpdatingUserIds) {
            for(Long permissionId : permissionIds) {
                userPolicyParticipantsToSave.add(new UserPolicyParticipantEntity(newUpdatingUserId, permissionId));
            }
        }
        userPolicyService.saveList(userPolicyParticipantsToSave);
    }

    public GroupEntity updateAndModifyParticipants(GroupDto requestBody, List<Long> newUserIds, List<Long> permissionIds) {
        Optional<GroupEntity> updatingGroupOptional = findByIdAndDeletedFalse(requestBody.getId());
        if(updatingGroupOptional.isPresent()){
            GroupEntity updatingGroup = updatingGroupOptional.get();
            if(newUserIds != null) {
                Set<Long> intersectUserGroupParticipantIds = userGroupService.findAllByGroupId(updatingGroup.getId())
                        .stream()
                        .map(UserGroupParticipantEntity::getUserId).collect(Collectors.toSet());
                intersectUserGroupParticipantIds.retainAll(newUserIds);
                List<Long> updatingIds = newUserIds.stream().filter(id -> !intersectUserGroupParticipantIds.contains(id)).collect(Collectors.toList());
                assignPermissionOfGroupToNewUsers(updatingIds, updatingGroup.getId());
            }

            if(permissionIds != null) {
                Set<Long> intersectGroupPolicyParticipantIds = groupPolicyService.findByGroupIdAndIsDeletedFalse(updatingGroup.getId())
                        .stream()
                        .map(GroupPolicyParticipantEntity::getPolicyId)
                        .collect(Collectors.toSet());
                intersectGroupPolicyParticipantIds.retainAll(newUserIds);
                List<Long> updatingPermissionIds = newUserIds.stream().filter(id -> !intersectGroupPolicyParticipantIds.contains(id)).collect(Collectors.toList());
                assignNewPermissionToGroup(updatingPermissionIds, updatingGroup.getId());
                assignNewPermissionToUserOfGroup(updatingPermissionIds, updatingGroup.getId());
            }
            return getUpdatedAttributesGroup(requestBody, updatingGroup);
        }
        return null;
    }

    private void assignNewPermissionToGroup(List<Long> updatingPermissionIds, Long groupId) {
        List<GroupPolicyParticipantEntity> updatingEntities = new ArrayList<>();
        for(Long updatingPermissionId : updatingPermissionIds) {
            updatingEntities.add(new GroupPolicyParticipantEntity(groupId, updatingPermissionId));
        }
        groupPolicyService.saveList(updatingEntities);
    }

    private void assignNewPermissionToUserOfGroup(List<Long> updatingPermissionIds, Long groupId) {
        List<UserPolicyParticipantEntity> updatingEntities = new ArrayList<>();
        List<Long> updatingUserIds = userGroupService.findAllByGroupId(groupId)
                .stream()
                .map(UserGroupParticipantEntity::getUserId).collect(Collectors.toList());
        for(Long userId : updatingUserIds) {
            for(Long permissionId : updatingPermissionIds) {
                updatingEntities.add(new UserPolicyParticipantEntity(userId, permissionId));
            }
        }
        userPolicyService.saveList(updatingEntities);
    }

    @Transactional
    public boolean deleteGroupByCodeAndModifyParticipants(UUID code) {
        GroupEntity deletingGroup = findByCodeAndIsDeleteFalse(code);
        deleteEntity(deletingGroup);
        userGroupService.deleteParticipantsByGroupId(deletingGroup.getId());
        groupPolicyService.deleteParticipantsByGroupId(deletingGroup.getId());
        return true;
    }

    public List<GroupDto> getGroupList() {
        List<GroupEntity> groupEntities = repository.findByIsDeletedFalse();
        List<GroupDto> results = new ArrayList<>();
        for(GroupEntity groupEntity : groupEntities) {
            GroupDto result = new GroupDto(groupEntity);
            result.setPermissions(getPermissionByGroupId(groupEntity.getId()));
            result.setNumberUsers(getNumberUsersByGroupId(groupEntity.getId()));
            results.add(result);
        }
        return results;
    }

    private List<String> getPermissionByGroupId(Long groupId) {
        List<Long> permissionIds = groupPolicyService.findByGroupIdAndIsDeletedFalse(groupId)
                .stream()
                .map(GroupPolicyParticipantEntity::getPolicyId).collect(Collectors.toList());
        List<String> permissions = permissionService.findByIdInAndDeletedFalse(permissionIds)
                .stream()
                .map(PermissionPolicyEntity::getName).collect(Collectors.toList());
        return permissions;
    }

    private int getNumberUsersByGroupId(Long groupId) {
        // maybe trùng, handle sau
        return userGroupService.findAllByGroupId(groupId).size();
    }

}

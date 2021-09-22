package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.*;
import com.rabiloo.custom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService<UserEntity, UserRepository> {

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserPolicyService userPolicyService;

    @Autowired
    GroupService groupService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    GroupPolicyService groupPolicyService;

    private void assignPermission(UserEntity userEntity, List<Long> permissionIds) {
        List<PermissionPolicyEntity> assignedPermissionEntities = permissionService.findByIdInAndDeletedFalse(permissionIds);
        for(PermissionPolicyEntity permissionPolicyEntity : assignedPermissionEntities) {
            userPolicyService.save(new UserPolicyParticipantEntity(userEntity.getId(), permissionPolicyEntity.getId(), permissionPolicyEntity.getName(), userEntity.getUsername()));
        }
    }

    private void assignGroup(UserEntity userEntity, List<Long> groupIds) {
        List<GroupEntity> assignedGroupEntities = groupService.findByIdInAndDeletedFalse(groupIds);
        for(GroupEntity groupEntity : assignedGroupEntities) {
            userGroupService.save(new UserGroupParticipantEntity(userEntity.getId(), groupEntity.getId(), userEntity.getUsername(), groupEntity.getName()));
        }

        assignPermissionsByGroup(userEntity.getId(), groupIds);
    }

    private void assignPermissionsByGroup(Long userId, List<Long> groupIds) {
        // PAUSE 2
        List<Long> permissionIdsByGroupIds = groupPolicyService.findAll
    }

    public UserEntity createUserAndModifyParticipant(UserEntity userEntity, List<Long> permissionIds, List<Long> groupIds) {
        UserEntity createdUser = save(userEntity);
        if(permissionIds != null) {
            assignPermission(createdUser, permissionIds);
        }
        if(groupIds != null) {
            assignGroup(createdUser, groupIds);
        }
        return createdUser;
    }

    public void clearPermission(Long userId) {
        userPolicyService.deleteParticipantsByUserId(userId);
    }

    public void clearGroup(Long userId) {
        userGroupService.deleteParticipantsByUserId(userId);
    }

    public UserEntity updateUserAndModifyParticipant(Long id, String username, String password, List<Long> permissionIds, List<Long> groupIds) {
        Optional<UserEntity> updatingUserOptional = repository.findByIdAndIsDeletedFalse(id);

        if(updatingUserOptional.isPresent()) {
            UserEntity updatingUser = updatingUserOptional.get();
            updatingUser.setUsername(username == null ? updatingUser.getUsername() : username);
            updatingUser.setPassword(password == null ? updatingUser.getPassword() : password);

            if(permissionIds != null) {
                clearPermission(updatingUser.getId());
                assignPermission(updatingUser, permissionIds);
            }

            if(groupIds != null) {
                clearGroup(updatingUser.getId());
                assignGroup(updatingUser, groupIds);
            }
            return updatingUser;
        }
        return null;
    }

    public boolean deleteUserAndModifyParticipant(Long id) {
        Optional<UserEntity> deletingUserOptional = repository.findByIdAndIsDeletedFalse(id);
        if(deletingUserOptional.isPresent()) {
            UserEntity deletingUser = deletingUserOptional.get();
            userPolicyService.deleteParticipantsByUserId(deletingUser.getId());
            userGroupService.deleteParticipantsByUserId(deletingUser.getId());
            return deleteEntity(deletingUser);
        }
        return false;
    }
}

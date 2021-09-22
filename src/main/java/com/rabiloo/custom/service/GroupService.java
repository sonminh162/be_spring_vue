package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.GroupEntity;
import com.rabiloo.custom.entity.UserEntity;
import com.rabiloo.custom.entity.UserGroupParticipantEntity;
import com.rabiloo.custom.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService extends BaseService<GroupEntity, GroupRepository> {

    @Autowired
    UserService userService;

    @Autowired
    UserGroupService userGroupService;

    private void addUsersToGroup(List<Long> userIds, GroupEntity groupEntity) {
        List<UserEntity> assignedUsers = userService.findByIdInAndDeletedFalse(userIds);
        if(assignedUsers != null && !assignedUsers.isEmpty()) {
            for(UserEntity userEntity : assignedUsers) {
                userGroupService.save(new UserGroupParticipantEntity(userEntity.getId(), groupEntity.getId(), userEntity.getUsername(), groupEntity.getName()));
            }
        }
    }

    // xem lai trong user service phần join group
    private void addPermissionsToGroup(List<Long> permissionIds, GroupEntity groupEntity) {
        // thay doi quyen cua group thi xu ly tat ca cac thang con

    }

    public GroupEntity createAndModifyParticipants(GroupEntity requestBody, List<Long> userIds, List<Long> permissionIds) {
        GroupEntity createdGroup = save(requestBody);
        if(createdGroup != null) {
            if(userIds != null) {
                addUsersToGroup(userIds, createdGroup);
            }
            if(permissionIds != null) {
                addPermissionsToGroup(permissionIds, createdGroup);
            }
            return createdGroup;
        }
        return null;
    }
}

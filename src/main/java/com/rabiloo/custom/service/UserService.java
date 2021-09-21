package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.PermissionPolicyEntity;
import com.rabiloo.custom.entity.UserEntity;
import com.rabiloo.custom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<UserEntity, UserRepository> {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionService permissionService;

    private void assignPermission(UserEntity userEntity, List<Long> permissionIds) {
//        for(Long permissionId : permissionIds) {
//            permissionService.save(permissionService.find)
//        }
        List<PermissionPolicyEntity> getPermissionEntities = permissionService.findByIdInAndDeletedFalse(permissionIds);
    }

    public UserEntity createUser(UserEntity userEntity, List<Long> permissionIds, List<Long> groupIds) {
        if(permissionIds != null) {
            assignPermission(userEntity, permissionIds);
        }
        return save(userEntity);
    }
}

package com.rabiloo.custom.web.resource;

import com.rabiloo.base.core.BaseResource;
import com.rabiloo.custom.entity.UserEntity;
import com.rabiloo.custom.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource extends BaseResource<UserService> {

    @GetMapping("/test")
    public String ping() {
        return "pong";
    }

    /**
     * -- uu tien logic truoc
     * -- convert Dto bao mat
     * -- Dat ten
     * -- them log sau khi hoan thanh
     * -- check dieu kien dau vao, dau ra dung chuan chua
     * -- moi 1 dong tac deu co muc dich di kem, ko viet code minh ko hieu sau can ke
     * -- tim cach base hoa module, them truong code vao cac entity roi format
     */

    @PostMapping("/create")
    public UserEntity createUser(@RequestBody UserEntity userEntity,
                                 @RequestParam(value = "permissionIds", required = false) List<Long> permissionIds,
                                 @RequestParam(value = "groupIds", required = false) List<Long> groupIds) {
        return service.createUserAndModifyParticipant(userEntity, permissionIds, groupIds);
    }

    @PostMapping("/update")
    public UserEntity updateUser(@RequestParam(value = "userId") Long id,
                                 @RequestParam(value = "username", required = false) String username,
                                 @RequestParam(value = "password", required = false) String password,
                                 @RequestParam(value = "permissionIds", required = false) List<Long> permissionIds,
                                 @RequestParam(value = "groupIds", required = false) List<Long> groupIds) {
        return service.updateUserAndModifyParticipant(id, username, password, permissionIds, groupIds);
    }

    @DeleteMapping("/delete")
    public boolean deleteUser(@RequestParam(value = "id") Long id) {
        return service.deleteUserAndModifyParticipant(id);
    }

}

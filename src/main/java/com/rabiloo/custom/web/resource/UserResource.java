package com.rabiloo.custom.web.resource;

import com.rabiloo.custom.entity.UserEntity;
import com.rabiloo.custom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public String ping() {
        return "pong";
    }

    /**
     -- uu tien logic truoc
     -- convert Dto bao mat
     -- Dat ten
     -- them log sau khi hoan thanh
     -- check dieu kien dau vao, dau ra dung chuan chua
     -- moi 1 dong tac deu co muc dich di kem, ko viet code minh ko hieu sau can ke
     -- tim cach base hoa module,
     */

    @PostMapping("/user/create")
    public UserEntity createUserApi(@RequestBody UserEntity userEntity,
                                    @RequestParam(value = "permissionIds", required = false) List<Long> permissionIds,
                                    @RequestParam(value = "groupIds", required = false) List<Long> groupIds) {
        return userService.createUser(userEntity, permissionIds, groupIds);
    }

}
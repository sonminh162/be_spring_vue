package com.rabiloo.custom.web.resource;

import com.rabiloo.base.core.BaseResource;
import com.rabiloo.custom.entity.GroupEntity;
import com.rabiloo.custom.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupResource extends BaseResource<GroupService> {

    @PostMapping("/create")
    public GroupEntity createGroup(@RequestBody GroupEntity groupEntity,
                                   @RequestParam(value = "userIds", required = false) List<Long> userIds,
                                   @RequestParam(value = "permissionIds", required = false) List<Long> permissionIds) {
        return service.createAndModifyParticipants(groupEntity, userIds, permissionIds);
    }

}

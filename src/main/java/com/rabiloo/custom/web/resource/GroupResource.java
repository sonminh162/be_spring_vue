package com.rabiloo.custom.web.resource;

import com.rabiloo.base.core.BaseResource;
import com.rabiloo.custom.dto.group.GroupDto;
import com.rabiloo.custom.entity.GroupEntity;
import com.rabiloo.custom.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupResource extends BaseResource<GroupService> {

    @PostMapping("/create")
    public GroupEntity createGroup(@RequestBody GroupEntity groupEntity,
                                   @RequestParam(value = "userIds", required = false) List<Long> userIds,
                                   @RequestParam(value = "permissionIds", required = false) List<Long> permissionIds) {
        return service.createAndModifyParticipants(groupEntity, userIds, permissionIds);
    }

    @PostMapping("/update")
    public GroupEntity updateGroup(@RequestBody GroupDto groupDto,
                                   @RequestParam(value = "userIds", required = false) List<Long> userIds,
                                   @RequestParam(value = "permissionIds", required = false) List<Long> permissionIds) {
        return service.updateAndModifyParticipants(groupDto, userIds, permissionIds);
    }

    @DeleteMapping("/delete")
    public boolean deleteGroup(@RequestParam(value = "groupCode") UUID groupCode) {
        return service.deleteGroupByCodeAndModifyParticipants(groupCode);
    }


//    @GetMapping("/") DOING
//    public List<GroupDto> getList() {
//        return service.getGroupList();
//    }
}

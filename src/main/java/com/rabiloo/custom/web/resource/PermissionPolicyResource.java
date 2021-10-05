package com.rabiloo.custom.web.resource;

import com.rabiloo.base.core.BaseResource;
import com.rabiloo.custom.dto.permission.PermissionPolicyDto;
import com.rabiloo.custom.entity.permission.PermissionPolicyEntity;
import com.rabiloo.custom.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/policy")
public class PermissionPolicyResource extends BaseResource<PermissionService> {

    @GetMapping("/")
    public List<PermissionPolicyDto> getPermissionList() {
        return service.getList();
    }

    @PostMapping("/create")
    public PermissionPolicyEntity createPermissionPolicy(@RequestBody PermissionPolicyEntity permissionPolicyEntity,
                                                         @RequestParam(value = "userIds", required = false) List<Long> userIds,
                                                         @RequestParam(value = "groupIds", required = false) List<Long> groupIds) {
        return service.createPermissionAndModifyParticipants(permissionPolicyEntity, userIds, groupIds);
    }
    @PostMapping("/update")
    public PermissionPolicyEntity updatePermissionPolicy(@RequestParam(value = "permissionCode") UUID code,
                                                         @RequestBody PermissionPolicyEntity permissionPolicyEntity,
                                                         @RequestParam(value = "userIds", required = false) List<Long> userIds,
                                                         @RequestParam(value = "groupIds", required = false) List<Long> groupIds) {
        return service.updatePermissionAndModifyParticipants(code, permissionPolicyEntity, userIds, groupIds);
    }

    @DeleteMapping("/delete")
    public boolean deletePermissionPolicy(@RequestParam(value = "code") UUID code) {
        return service.deletePermissionPolicyAndModifyParticipants(code);
    }
}

package com.rabiloo.custom.dto.permission;

import com.rabiloo.custom.entity.permission.PermissionPolicyEntity;
import com.rabiloo.custom.entity.permission.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionPolicyDto {
    private String name;
    private String resourceType;
    private String resourceId;
    private ActionType action;
    private String description;

    public PermissionPolicyDto(PermissionPolicyEntity entity) {
        this.name = entity.getName();
        this.resourceType = entity.getResourceType();
        this.resourceId = entity.getResourceId();
        this.action = entity.getAction();
        this.description = entity.getDescription();
    }
}

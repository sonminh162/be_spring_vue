package com.rabiloo.custom.entity.permission;

import com.rabiloo.base.core.BaseEntity;
import com.rabiloo.custom.entity.permission.enums.ActionType;

import javax.persistence.*;

@Entity
@Table(name = "permission_policy", schema = "mydb")
public class PermissionPolicyEntity extends BaseEntity {
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ActionType action;
    private String resourceType;
    private String resourceId;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "action")
    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    @Basic
    @Column(name = "resource_type")
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "resource_id")
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


}

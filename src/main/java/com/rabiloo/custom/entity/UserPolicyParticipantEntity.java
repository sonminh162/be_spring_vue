package com.rabiloo.custom.entity;

import com.rabiloo.base.core.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "user_policy_participant", schema = "mydb")
public class UserPolicyParticipantEntity extends BaseEntity {
    private Long userId;
    private Long policyId;

    @Transient
    private String permissionName;

    @Transient
    private String userName;

    public UserPolicyParticipantEntity(Long userId, Long policyId, String permissionName, String userName) {
        this.userId = userId;
        this.policyId = policyId;
        this.permissionName = permissionName;
        this.userName = userName;
    }

    public UserPolicyParticipantEntity(Long userId, Long policyId) {
        this.userId = userId;
        this.policyId = policyId;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "policy_id")
    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}

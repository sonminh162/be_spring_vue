package com.rabiloo.custom.entity;

import com.rabiloo.base.core.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "group_policy_participant", schema = "mydb")
public class GroupPolicyParticipantEntity extends BaseEntity {
    private Long groupId;
    private Long policyId;

    public GroupPolicyParticipantEntity(Long groupId, Long policyId) {
        this.groupId = groupId;
        this.policyId = policyId;
    }

    @Basic
    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "policy_id")
    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

}

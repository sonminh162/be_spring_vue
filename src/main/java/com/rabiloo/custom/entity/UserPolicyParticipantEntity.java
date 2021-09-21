package com.rabiloo.custom.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_policy_participant", schema = "mydb")
public class UserPolicyParticipantEntity extends BaseEntity{
    private Integer userId;
    private Integer policyId;

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "policy_id")
    public Integer getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Integer policyId) {
        this.policyId = policyId;
    }

}

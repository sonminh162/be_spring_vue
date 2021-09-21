package com.rabiloo.custom.entity;

import com.rabiloo.base.core.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "user_group_participant", schema = "mydb")

public class UserGroupParticipantEntity extends BaseEntity {
    private Long userId;
    private Long groupId;

    @Transient
    private String userName;

    @Transient
    private String groupName;

    public UserGroupParticipantEntity(Long userId, Long groupId, String userName, String groupName) {
        this.userId = userId;
        this.groupId = groupId;
        this.userName = userName;
        this.groupName = groupName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    @Basic
    @Column(name = "group_id")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}

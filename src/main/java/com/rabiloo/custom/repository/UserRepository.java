package com.rabiloo.custom.repository;

import com.rabiloo.base.core.BaseRepository;
import com.rabiloo.custom.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends BaseRepository<UserEntity> {

    @Query(value = "select ue from UserEntity as ue join UserGroupParticipantEntity as ugpe " +
            "on ue.id = ugpe.userId " +
            "where ugpe.groupId = :groupId and ue.isDeleted = false  and ugpe.isDeleted = false")
    List<UserEntity> findByGroupIdAndIsDeletedFalse(Long groupId);

}

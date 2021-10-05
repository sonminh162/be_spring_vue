package com.rabiloo.custom.repository;

import com.rabiloo.base.core.BaseRepository;
import com.rabiloo.custom.entity.GroupEntity;

import java.util.List;

public interface GroupRepository extends BaseRepository<GroupEntity> {
    List<GroupEntity> findByIsDeletedFalse();
}

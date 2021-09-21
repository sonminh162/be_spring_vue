package com.rabiloo.custom.repository;

import com.rabiloo.base.core.BaseRepository;
import com.rabiloo.custom.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends BaseRepository<UserEntity> {
}

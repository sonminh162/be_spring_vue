package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.UserGroupParticipantEntity;
import com.rabiloo.custom.repository.UserGroupRepository;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService extends BaseService<UserGroupParticipantEntity, UserGroupRepository> {
}

package com.rabiloo.custom.service;

import com.rabiloo.base.core.BaseService;
import com.rabiloo.custom.entity.UserGroupParticipantEntity;
import com.rabiloo.custom.entity.UserPolicyParticipantEntity;
import com.rabiloo.custom.repository.UserPolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPolicyService extends BaseService<UserPolicyParticipantEntity, UserPolicyRepository> {

    public UserPolicyParticipantEntity createUserPolicyParticipant(UserPolicyParticipantEntity userPolicyParticipantEntity) {
        return repository.save(userPolicyParticipantEntity);
    }
}

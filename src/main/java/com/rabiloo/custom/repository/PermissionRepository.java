package com.rabiloo.custom.repository;

import com.rabiloo.base.core.BaseRepository;
import com.rabiloo.custom.entity.permission.PermissionPolicyEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends BaseRepository<PermissionPolicyEntity> {

    @Query(value = "select ppe from PermissionPolicyEntity as ppe join UserPolicyParticipantEntity as pppe " +
            "on ppe.id = pppe.policyId " +
            "where pppe.userId = :userId and ppe.isDeleted = false  and pppe.isDeleted = false")
    List<PermissionPolicyEntity> findAllByUserIdAndIsDeletedFalse(Long userId);

    List<PermissionPolicyEntity> findByIsDeletedFalse();

}

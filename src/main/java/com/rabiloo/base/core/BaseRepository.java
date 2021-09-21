package com.rabiloo.base.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository <E extends BaseEntity> extends PagingAndSortingRepository<E, Long>, JpaRepository<E, Long> {
    E findByCode(UUID code);

    Page<E> findByIsDeleted(Pageable page, boolean isDeleted);

    Optional<E> findByIdAndIsDeletedFalse(Long id);

    List<E> findByIdInAndIsDeletedFalse(Collection<Long> id);


    Optional<E> findByCodeAndIsDeletedFalse(UUID code);

    List<E> findByIdInAndIsDeletedFalse(List<Long> ids);

    List<E> findByCodeIn(List<UUID> codes);
}

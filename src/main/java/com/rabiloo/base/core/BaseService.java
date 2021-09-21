package com.rabiloo.base.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public abstract class BaseService<E extends BaseEntity, R extends BaseRepository<E>> {

    @Autowired
    protected R repository;

    public E save(E entity) {
        if (entity == null) {
            return null;
        }

        preSave(entity);

        return (E) repository.save(entity);
    }

    public List<E> findByIdInAndDeletedFalse(Collection<Long> ids) {
        return repository.findByIdInAndIsDeletedFalse(ids);
    }



    public void preSave(E entity) {
        if(entity.getId() == null) {
            entity.setCreatedTime(new Date());
        } else {
            entity.setUpdatedTime(new Date());
        }
    }
}

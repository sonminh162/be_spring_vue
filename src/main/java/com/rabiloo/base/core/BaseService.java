package com.rabiloo.base.core;

import com.rabiloo.custom.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public E findByCodeAndIsDeleteFalse(UUID code) {
        return repository.findByCodeAndIsDeletedFalse(code).orElseThrow(()-> new ResourceNotFoundException(""));
    }

    public Optional<E> findByIdAndDeletedFalse(Long id) {
        return repository.findByIdAndIsDeletedFalse(id);
    }

    public List<E> saveList(List<E> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        List<E> entitiesToSave = new ArrayList<>();
        for (E entity : entities) {
            if (entity != null) {
                preSave(entity);
                entitiesToSave.add(entity);
            }
        }
        return repository.saveAll(entitiesToSave);
    }

    public boolean deleteEntity(E entity) {
        if(entity == null) {
            return false;
        } else {
            entity.setDeleted(true);
            repository.save(entity);
            return true;
        }
    }

    public boolean deleteList(List<E> entities) {

        if (entities == null || entities.isEmpty()) {
            return false;

        } else {
            List<E> entitiesToDelete = new ArrayList<>();
            for (E entity : entities) {
                if (entity != null) {
                    entity.setDeleted(true);
                }

                entitiesToDelete.add(entity);
            }

            repository.saveAll(entitiesToDelete);

            return true;
        }
    }

    public void preSave(E entity) {
        if(entity.getId() == null) {
            entity.setCreatedTime(new Date());
        } else {
            entity.setUpdatedTime(new Date());
        }
    }
}

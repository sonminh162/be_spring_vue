package com.rabiloo.base.core;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    protected Date createdTime;

    protected Date updatedTime;

    protected Long createdByUserId;

    protected Long updatedByUserId;

    protected boolean isDeleted;
}

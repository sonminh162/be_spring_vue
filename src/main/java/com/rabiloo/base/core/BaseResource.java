package com.rabiloo.base.core;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseResource<S extends BaseService> {
    @Autowired
    protected S service;

}

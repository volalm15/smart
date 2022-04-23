package com.vollify.smart.controller.service.common;

import org.assertj.core.util.Lists;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractService<T> {

    @Transactional(readOnly = true)
    public Optional<T> findById(final String id) {
        return getDao().findById(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    public T create(final T entity) {
        getDao().save(entity);
        return entity;
    }

    public T update(final T entity) {
        getDao().save(entity);
        return entity;
    }

    public void delete(final String entityId) {
        getDao().deleteById(entityId);
    }

    protected abstract MongoRepository<T, String> getDao();
}
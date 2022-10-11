package com.nix.zhylina.dao;

import java.util.List;

/**
 * Interface that contains methods create, update, remove, findAll and findById.
 * These methods will be overridden by jdbcRoleDao and jdbcUserDao.
 *
 * @param <E> parameter that will be specified by RoleDao and UserDao
 */
public interface Dao<E> {
    void create(E entity);

    void update(E entity);

    void remove(E entity);

    List<E> findAll();

    E findById(Long id);
}

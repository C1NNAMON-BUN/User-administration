package com.nix.zhylina.dao;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public interface Dao <E> {
    void create(E entity);

    void update(E entity);

    void remove(E entity);
}

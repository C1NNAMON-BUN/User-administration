package com.nix.zhylina.services.hibernateService.impl;

import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<UserEntity> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    @Override
    public void create(UserEntity entity) {
        userDao.create(entity);
    }

    @Override
    public void update(UserEntity entity) {
        userDao.update(entity);
    }

    @Override
    public void remove(UserEntity entity) {
        userDao.remove(entity);
    }
}

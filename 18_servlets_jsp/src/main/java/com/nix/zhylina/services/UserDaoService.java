package com.nix.zhylina.services;

import com.nix.zhylina.dao.UserDao;
import com.nix.zhylina.entitie.User;

import java.util.List;

/**
 * A class that represents the user operations services that are in the database.
 * This class includes the implementation of class methods {@link com.nix.zhylina.dao.impl.JdbcUserDao}
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 22.02.2022
 */
public class UserDaoService implements UserDao {
    private final UserDao userDao;

    public UserDaoService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void create(User user) {
        userDao.create(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void remove(User user) {
        userDao.remove(user);
    }
}

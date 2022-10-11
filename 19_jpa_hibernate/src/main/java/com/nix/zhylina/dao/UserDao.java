package com.nix.zhylina.dao;

import com.nix.zhylina.entitie.User;

/**
 * <p><h2>UserDao</h2>
 * <b>Interface that extends {@link Dao} methods</b>
 * <br>
 *
 * @author Zhilina Svetlana
 * @version 3.0
 * @since 08.03.2022
 */
public interface UserDao extends Dao<User> {
    User findByLogin(String login);

    User findByEmail(String email);
}

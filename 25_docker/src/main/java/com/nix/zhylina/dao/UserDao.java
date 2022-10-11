package com.nix.zhylina.dao;

import com.nix.zhylina.entities.User;

import java.sql.SQLException;

/**
 * <p><h2>UserDao</h2>
 * <b>Interface that extends {@link Dao} methods</b><br>
 *
 * @author Zhilina Svetlana
 * @version1.0
 * @since 26.01.2022 </p>
 */
public interface UserDao extends Dao<User> {
    /**
     * The method in which it is necessary to implement a search for a tuple
     * by login, equivalent to {@code login}
     *
     * @param login login that will be used to search for an entry in
     *              database
     * @return object that contains the found entry
     * @throws SQLException throw own exception on error
     */
    User findByLogin(String login) throws SQLException;

    /**
     * The method in which it is necessary to implement the search for a
     * tuple by email,equivalent to {@code email}
     *
     * @param email The email that will be used to search for a record in
     *              database
     * @return object that contains the found entry
     * @throws SQLException throw own exception on error
     */
    User findByEmail(String email) throws SQLException;
}

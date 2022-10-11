package com.nix.zhylina.dao;

import com.nix.zhylina.entities.Role;

import java.sql.SQLException;

/**
 * <p><h2>RoleDao</h2>
 * <b>Interface that extends {@link Dao} methods</b>
 * <br>
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 26.01.2022 </p>
 */
public interface RoleDao extends Dao<Role> {
    /**
     * This method must search for a tuple in the database by
     * name
     *
     * @param name the object with the given name must be found in the database
     * @return read from database object
     * @throws SQLException throw own exception on error
     */
    Role findByName(String name) throws SQLException;
}
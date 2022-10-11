package com.nix.zhylina.dao;

import com.nix.zhylina.entitie.Role;

/**
 * <p><h2>RoleDao</h2>
 * <b>Interface that extends {@link Dao} methods</b>
 * <br>
 *
 * @author Zhilina Svetlana
 * @version 3.0
 * @since 08.03.2022
 */
public interface RoleDao extends Dao<Role> {
    Role findByName(String name);
}

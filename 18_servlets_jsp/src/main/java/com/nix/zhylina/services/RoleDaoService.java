package com.nix.zhylina.services;

import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.entitie.Role;

import java.util.List;

/**
 * A class that represents role operations services that are stored in a database.
 * This class includes the implementation of class methods {@link com.nix.zhylina.dao.impl.JdbcRoleDao}
 *
 * @author Zhilina Svetlana
 * @version 1.0
 * @since 22.02.2022
 */
public class RoleDaoService implements RoleDao {
    private final RoleDao roleDao;

    public RoleDaoService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public void create(Role role) {
        roleDao.create(role);
    }

    public void update(Role role) {
        roleDao.update(role);
    }

    public void remove(Role role) {
        roleDao.remove(role);
    }

    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}

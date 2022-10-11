package com.nix.zhylina.services.hibernateService.impl;

import com.nix.zhylina.services.hibernateService.IRoleService;
import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.models.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RoleService implements IRoleService {
    private RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleDao.findAll();
    }
}

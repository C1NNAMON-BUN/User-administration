package com.nix.zhylina.services.hibernateService.impl;

import com.nix.zhylina.dao.RoleDao;
import com.nix.zhylina.models.RoleEntity;
import com.nix.zhylina.services.hibernateService.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    private RoleDao<RoleEntity> roleDao;

    @Autowired
    public RoleService(RoleDao<RoleEntity> roleDao) {
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

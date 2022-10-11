package com.nix.zhylina.services.hibernateService;

import com.nix.zhylina.models.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Optional<RoleEntity> findById(Long id);

    Optional<RoleEntity> findByName(String name);

    List<RoleEntity> findAll();
}

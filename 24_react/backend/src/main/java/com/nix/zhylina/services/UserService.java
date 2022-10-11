package com.nix.zhylina.services;

import com.nix.zhylina.dao.impl.HibernateUserDao;
import com.nix.zhylina.models.UserEntity;
import com.nix.zhylina.services.hibernateService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static com.nix.zhylina.utils.Constants.CONST_AUTHORITY_ROLE_PREFIX;

@Component("userDetailsService")
public class UserService implements UserDetailsService {
    private final IUserService userService;

    @Autowired
    public UserService(IUserService userService) {
        this.userService = userService;
    }

    public Optional <UserEntity> findByUsername(String username) {
        return userService.findByLogin(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = findByUsername(username).get();

        return new User(userEntity.getLogin(), userEntity.getPassword(), mapRolesToAuthorities(userEntity));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserEntity userEntity) {
        return Collections.singleton(new SimpleGrantedAuthority(CONST_AUTHORITY_ROLE_PREFIX + userEntity.getRoleEntity()
                .getName().toUpperCase()));
    }
}

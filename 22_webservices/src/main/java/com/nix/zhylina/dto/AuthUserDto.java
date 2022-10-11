package com.nix.zhylina.dto;

import com.nix.zhylina.models.RoleEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

@Data
public class AuthUserDto {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    private RoleEntity roleEntity;
    private Collection<? extends GrantedAuthority> authorities;
}

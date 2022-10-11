package com.nix.zhylina.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
public class UserDto {
    private String id;
    private String login;
    private String firstName;
    private String lastName;
    private String birthday;
    private String roleEntity;
}

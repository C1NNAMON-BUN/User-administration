package com.nix.zhylina.dto;

import com.nix.zhylina.models.UserEntity;

public class ConverterEntity {
    private ConverterEntity(){}
    public static UserDto convertUserEntityToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId().toString());
        userDto.setLogin(userEntity.getLogin());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setBirthday(String.valueOf(userEntity.getUserAge()));
        userDto.setRoleEntity(userEntity.getRoleEntity().getName());

        return userDto;
    }
}

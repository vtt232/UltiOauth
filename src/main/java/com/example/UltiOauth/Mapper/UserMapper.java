package com.example.UltiOauth.Mapper;

import com.example.UltiOauth.DTO.UserDTO;
import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Entity.UserRole;

public class UserMapper {
    public static UserEntity fromDtoToEntity(UserDTO userDTO, UserEntity userEntity){
        return userEntity.toBuilder().username(userDTO.getLogin()).link(userDTO.getUrl()).build();
    }

    public static UserDTO fromEntityDto(UserEntity userEntity){
        UserRole userRole = userEntity.getRole();
        return new UserDTO(userEntity.getUsername(), userEntity.getLink(), userRole.name());
    }
}

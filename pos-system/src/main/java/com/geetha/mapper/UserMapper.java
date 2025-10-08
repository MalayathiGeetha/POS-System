package com.geetha.mapper;

import com.geetha.modal.User;
import com.geetha.payload.dto.UserDto;

public class UserMapper {
    public static UserDto toDTO(User savedUser) {
        UserDto userDto=new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());
        userDto.setPhone(savedUser.getPhone());
        userDto.setBranchId(savedUser.getBranch()!=null?savedUser.getBranch().getId():null);
        userDto.setStoreId(savedUser.getStore()!=null?savedUser.getStore().getId():null);
        return userDto;
    }

    public  static User toEntity(UserDto userDto){
        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setRole(userDto.getRole());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());
        user.setLastLogin(userDto.getLastLogin());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());

        return user;
    }

}

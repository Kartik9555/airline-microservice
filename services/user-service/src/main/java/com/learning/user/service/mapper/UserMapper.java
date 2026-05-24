package com.learning.user.service.mapper;

import com.learning.common.payload.dto.UserDTO;
import com.learning.user.service.model.User;

public class UserMapper {

    public static UserDTO toUser(final User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .phone(user.getPhone())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public static User toUser(final UserDTO user) {
        if (user == null) return null;
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .phone(user.getPhone())
                .lastLogin(user.getLastLogin())
                .build();
    }
}

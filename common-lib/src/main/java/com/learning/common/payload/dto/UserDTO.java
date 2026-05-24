package com.learning.common.payload.dto;

import com.learning.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private UserRole role;
    private String phone;
    private Instant lastLogin;
}

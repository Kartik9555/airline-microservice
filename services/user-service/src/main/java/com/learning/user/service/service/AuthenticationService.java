package com.learning.user.service.service;

import com.learning.common.payload.dto.UserDTO;
import com.learning.common.payload.response.AuthResponse;

public interface AuthenticationService {
    AuthResponse signup(UserDTO user) throws Exception;
    AuthResponse login(String username, String password) throws Exception;
}

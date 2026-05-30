package com.learning.booking.service.service.outbound;

import com.learning.booking.service.client.UserServiceClient;
import com.learning.common.payload.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOutboundService {
    private final UserServiceClient userClient;

    public UserDTO getUserById(Long userId) {
        return userClient.getUserById(userId);
    }
}

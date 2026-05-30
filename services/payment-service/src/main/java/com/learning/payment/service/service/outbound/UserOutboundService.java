package com.learning.payment.service.service.outbound;

import com.learning.common.payload.dto.UserDTO;
import com.learning.payment.service.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOutboundService {

    private final UserServiceClient client;

    public UserDTO getUserById(long id) {
        return client.getUserById(id);
    }
}

package com.learning.user.service.service.impl;

import com.learning.common.payload.dto.UserDTO;
import com.learning.user.service.mapper.UserMapper;
import com.learning.user.service.repository.UserRepository;
import com.learning.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByEmail(String email) throws Exception {
        return UserMapper.toUser(userRepository.findUserByEmail(email)
                .orElseThrow(
                        () -> new Exception("User with email " + email + " not found")
                ));
    }

    @Override
    public UserDTO getUserById(Long id) throws Exception {
        return UserMapper.toUser(userRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("User with id " + id + " not found")
                ));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(UserMapper::toUser).toList();
    }
}

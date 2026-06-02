package com.learning.user.service.service.impl;

import com.learning.common.enums.UserRole;
import com.learning.common.payload.dto.UserDTO;
import com.learning.common.payload.response.AuthResponse;
import com.learning.user.service.mapper.UserMapper;
import com.learning.user.service.model.User;
import com.learning.user.service.repository.UserRepository;
import com.learning.user.service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderService provider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponse signup(UserDTO user) throws Exception {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new Exception("User with email " + user.getEmail() + " already exists");
        }

        if (user.getRole().equals(UserRole.ROLE_SYSTEM_ADMIN)) {
            throw new Exception("You are not allowed to perform this action");
        }

        final User newUser = UserMapper.toUser(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setLastLogin(Instant.now());

        final User saved = userRepository.save(newUser);

        final Authentication authentication = new UsernamePasswordAuthenticationToken(saved.getEmail(), saved.getPassword());
        return AuthResponse.builder()
                .token(provider.generateToken(authentication, saved.getId()))
                .message("Registered Successfully")
                .title("Welcome " + saved.getFullName())
                .user(UserMapper.toUser(saved))
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(String username, String password) throws Exception {
        Authentication authentication = authenticate(username, password);
        final User user = userRepository.findUserByEmail(username).orElseThrow(() -> new Exception("Invalid username or password"));
        user.setLastLogin(Instant.now());
        userRepository.save(user);
        return AuthResponse.builder()
                .token(provider.generateToken(authentication, user.getId()))
                .message("Login Successfully")
                .title("Welcome back " + user.getFullName())
                .user(UserMapper.toUser(user))
                .build();
    }

    private Authentication authenticate(String username, String password) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

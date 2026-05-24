package com.learning.user.service.controller;

import com.learning.common.payload.dto.UserDTO;
import com.learning.common.payload.request.LoginRequest;
import com.learning.common.payload.response.AuthResponse;
import com.learning.user.service.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(authenticationService.signup(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.login(request.getEmail(), request.getPassword()));
    }
}

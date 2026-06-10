package com.learning.user.service.service.runner;

import com.learning.common.enums.UserRole;
import com.learning.user.service.model.User;
import com.learning.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        final String email = "admin@gmail.com";
        final String password = "admin";
        log.info("DataInitializer is running...");
        if(userRepository.findUserByEmail(email).isPresent()) {
            log.info("User with email " + email + " already exists");
        } else {
            User user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .fullName("Admin User")
                    .role(UserRole.ROLE_SYSTEM_ADMIN)
                    .build();
            userRepository.save(user);
            log.info("Admin user with email " + email + " created successfully");
        }
    }
}

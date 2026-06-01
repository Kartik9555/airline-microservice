package com.learning.notification.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.learning")
public class NotificationServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}

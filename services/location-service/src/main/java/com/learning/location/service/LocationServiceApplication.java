package com.learning.location.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.learning")
public class LocationServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(LocationServiceApplication.class, args);
    }
}

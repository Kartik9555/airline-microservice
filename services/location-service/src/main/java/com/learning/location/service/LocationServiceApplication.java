package com.learning.location.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableCaching
public class LocationServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(LocationServiceApplication.class, args);
    }
}

package com.learning.airline.core.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableJpaAuditing
@EnableCaching
public class AirlineCoreServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(AirlineCoreServiceApplication.class, args);
    }

}

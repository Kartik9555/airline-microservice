package com.learning.ancillary.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableJpaAuditing
public class AncillaryServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(AncillaryServiceApplication.class, args);
    }
}

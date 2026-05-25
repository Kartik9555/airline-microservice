package com.learning.pricing.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableJpaAuditing
public class PricingServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

}

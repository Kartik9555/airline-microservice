package com.learning.seat.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableJpaAuditing
public class SeatServiceApplication {
    static void main(String[] args) {
        SpringApplication.run(SeatServiceApplication.class, args);
    }
}

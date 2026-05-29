package com.learning.booking.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableJpaAuditing
@EnableFeignClients
public class BookingServiceApplication {
    static void main() {
        SpringApplication.run(BookingServiceApplication.class);
    }
}

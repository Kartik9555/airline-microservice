package com.learning.flight.ops.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableJpaAuditing
@EnableFeignClients
public class FlightOpsServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(FlightOpsServiceApplication.class, args);
    }

}
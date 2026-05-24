package com.learning.flight.ops.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableScheduling
public class FlightOpsServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(FlightOpsServiceApplication.class, args);
    }

}
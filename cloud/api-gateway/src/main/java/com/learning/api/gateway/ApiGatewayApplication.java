package com.learning.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.learning")
public class ApiGatewayApplication {
    static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

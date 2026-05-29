package com.learning.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "com.learning")
@EnableEurekaServer
public class ServiceRegistryApplication {

    static void main(String[] args) {
        SpringApplication.run(ServiceRegistryApplication.class, args);
    }
}

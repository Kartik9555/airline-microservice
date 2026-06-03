package com.learning.api.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.function.Function;

@Configuration
public class KeyResolverConfig {

    @Bean
    Function<ServerRequest, String> ipKeyResolver() {

        return request -> {

            String ip = request.headers()
                    .firstHeader("X-Forwarded-For");

            if (ip != null) {
                ip = ip.split(",")[0].trim();
            } else {
                ip = request.servletRequest().getRemoteAddr();
            }

            return "ip:" + ip;
        };
    }

    @Bean
    Function<ServerRequest, String> userKeyResolver(JwtUtil jwtService) {

        return request -> {

            String auth = request.headers()
                    .firstHeader("Authorization");

            if (auth == null || !auth.startsWith("Bearer ")) {
                return "anonymous";
            }

            try {
                Long userId = jwtService.extractUserId(auth.substring(7));
                return "user:" + userId;
            } catch (Exception e) {
                return "invalid";
            }
        };
    }
}

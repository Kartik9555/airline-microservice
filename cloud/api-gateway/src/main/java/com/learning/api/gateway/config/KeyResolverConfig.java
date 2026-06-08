package com.learning.api.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class KeyResolverConfig {

    @Bean(name = "keyResolver")
    public KeyResolver keyResolver() {
        return exchange -> {

            // 1. Try JWT user
            String userId = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-User-Id");

            if (userId != null && !userId.isBlank()) {
                return Mono.just("USER_" + userId);
            }

            // 2. Try forwarded IP
            String ip = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-Forwarded-For");

            if (ip != null && !ip.isBlank()) {
                ip = ip.split(",")[0].trim();
                return Mono.just("IP_" + ip);
            }

            // 3. Fallback to remote address
            if (exchange.getRequest().getRemoteAddress() != null) {
                String fallbackIp = exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress();

                return Mono.just("IP_" + fallbackIp);
            }

            return Mono.just("UNKNOWN");
        };
    }

    @Bean
    public GlobalFilter timingFilter() {
        return (exchange, chain) -> {
            long start = System.currentTimeMillis();
            return chain.filter(exchange)
                    .doFinally(signalType -> {
                        long time = System.currentTimeMillis() - start;
                        System.out.println("GATEWAY TIME: " + time + "ms " +
                                exchange.getRequest().getURI());
                    });
        };
    }
}

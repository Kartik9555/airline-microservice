package com.learning.api.gateway.filters;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleGatewayFilterFactory
        extends AbstractGatewayFilterFactory<RoleGatewayFilterFactory.Config> {

    public RoleGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public @NonNull GatewayFilter apply(@NonNull Config config) {

        return (exchange, chain) -> {

            String roles = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-User-Roles");

            if (roles == null) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse().setComplete();
            }

            Set<String> roleSet = Arrays.stream(roles.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());

            if (!roleSet.contains(config.getRole())) {
                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    @Setter
    @Getter
    public static class Config {
        private String role;

    }
}
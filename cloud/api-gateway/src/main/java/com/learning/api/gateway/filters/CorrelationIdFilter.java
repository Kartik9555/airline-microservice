package com.learning.api.gateway.filters;

import org.jspecify.annotations.NonNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements GlobalFilter {

    private static final String HEADER = "X-Correlation-Id";

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String correlationId = Optional.ofNullable(
                exchange.getRequest().getHeaders().getFirst(HEADER)
        ).orElse(UUID.randomUUID().toString());

        ServerHttpRequest mutated = exchange.getRequest()
                .mutate()
                .header(HEADER, correlationId)
                .build();

        return chain.filter(exchange.mutate().request(mutated).build());
    }
}

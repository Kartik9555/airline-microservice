package com.learning.api.gateway.filters;

import com.learning.api.gateway.config.JwtUtil;
import com.learning.api.gateway.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.jspecify.annotations.NonNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;
    private final TokenBlacklistService blacklistService;

    @Override
    public @NonNull Mono<Void> filter(
            ServerWebExchange exchange,
            @NonNull GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();

        // Skip public endpoints
        if (path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing token");
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            return unauthorized(exchange, "Invalid token");
        }

        if (blacklistService.isBlacklisted(token)) {
            return unauthorized(exchange, "Token revoked");
        }

        String email = jwtUtil.extractEmail(token);
        String authorities = jwtUtil.extractAuthorities(token);
        Long userId = jwtUtil.extractUserId(token);

        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Email", email)
                .header("X-User-Roles", authorities)
                .build();

        return chain.filter(
                exchange.mutate()
                        .request(request)
                        .build());
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private Mono<Void> unauthorized(
            ServerWebExchange exchange,
            String message) {

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(bytes);

        return exchange.getResponse()
                .writeWith(Mono.just(buffer));
    }
}

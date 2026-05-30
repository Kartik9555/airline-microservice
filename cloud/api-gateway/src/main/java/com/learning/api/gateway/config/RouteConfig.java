package com.learning.api.gateway.config;

import com.learning.common.enums.UserRole;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {

    private final JwtUtil jwtUtil;

    public RouteConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RouterFunction<ServerResponse> authRoutes() {
        return GatewayRouterFunctions.route("auth-routes")
                .route(RequestPredicates.path("/auth/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("user-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> adminLocationServiceRoutes() {
        return GatewayRouterFunctions.route("admin-location-routes")
                .route(RequestPredicates.POST("/api/v1/cities/**"), HandlerFunctions.http())
                .route(RequestPredicates.POST("/api/v1/airports/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("location-service"))
                .before(this::jwtAuthFilter)
                .before(request -> requireRole(request, UserRole.ROLE_SYSTEM_ADMIN.toString()))
                .build();
    }

    @Bean
    @Order(1)
    public RouterFunction<ServerResponse> adminAirlineCoreServiceRoutes() {
        return GatewayRouterFunctions.route("admin-airline-core-routes")
                .route(RequestPredicates.GET("/api/v1/airlines/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("airline-core-service"))
                .before(this::jwtAuthFilter)
                .before(request -> requireRole(request, UserRole.ROLE_SYSTEM_ADMIN.toString()))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return GatewayRouterFunctions.route("user-service-routes")
                .route(RequestPredicates.path("/api/v1/users/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("user-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    @Order(2)
    public RouterFunction<ServerResponse> airlineCoreServiceRoutes() {
        return GatewayRouterFunctions.route("airline-core-routes")
                .route(RequestPredicates.path("/api/v1/airlines/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/aircrafts/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("airline-core-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> seatServiceRoutes() {
        return GatewayRouterFunctions.route("seat-service-routes")
                .route(RequestPredicates.path("/api/v1/cabin-classes/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/seat-maps/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/seats/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/seat-instances/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight-instance-cabins/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("seat-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> flightOpsServiceRoutes() {
        return GatewayRouterFunctions.route("flight-ops-service-routes")
                .route(RequestPredicates.path("/api/v1/flights/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight-instances/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight-schedules/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("flight-ops-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> pricingServiceRoutes() {
        return GatewayRouterFunctions.route("pricing-service-routes")
                .route(RequestPredicates.path("/api/v1/fares/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/fare-rules/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/baggage-policies/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("pricing-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> ancillaryServiceRoutes() {
        return GatewayRouterFunctions.route("ancillary-service-routes")
                .route(RequestPredicates.path("/api/v1/meals/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/ancillaries/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/insurance-coverages/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight-meals/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/flight-cabin-ancillaries/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("ancillary-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    @Order(2)
    public RouterFunction<ServerResponse> locationServiceRoutes() {
        return GatewayRouterFunctions.route("location-service-routes")
                .route(RequestPredicates.path("/api/v1/cities/**"), HandlerFunctions.http())
                .route(RequestPredicates.path("/api/v1/airports/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("location-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceRoutes() {
        return GatewayRouterFunctions.route("booking-service-routes")
                .route(RequestPredicates.path("/api/v1/bookings/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("booking-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoutes() {
        return GatewayRouterFunctions.route("payment-service-routes")
                .route(RequestPredicates.path("/api/v1/payments/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("payment-service"))
                .before(this::jwtAuthFilter)
                .build();
    }

    private ServerRequest jwtAuthFilter(ServerRequest request) {
        final String authHeader = request.headers().firstHeader(JwtConstant.JWT_HEADER);

        // check auth header exists
        if(authHeader == null || !authHeader.startsWith(JwtConstant.TOKEN_PREFIX)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid authorization header");
        }

        // remove prefix from token
        final String token = authHeader.substring(JwtConstant.TOKEN_PREFIX.length());

        // validate token
        if(!jwtUtil.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        // grab user info from token
        String email = jwtUtil.extractEmail(token);
        String authorities = jwtUtil.extractAuthorities(token);
        Long userId = jwtUtil.extractUserId(token);

        return ServerRequest.from(request)
                .header("X-User-Id", String.valueOf(userId))
                .header("X-User-Email", email)
                .header("X-User-Roles", authorities)
                .build();
    }

    private ServerRequest requireRole(ServerRequest request, String role) {
        String rolesHeader = request.headers().firstHeader("X-User-Roles");
        if (rolesHeader == null || !rolesHeader.contains(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied, required role: " + role);
        }
        return request;
    }
}

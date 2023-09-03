package com.simbirsoft.gateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthRoute {

    @Value("${application.services.authorization.url}")
    private String authUrl;

    @Bean
    public RouteLocator authRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "loginRoute",
                        route -> route.path(
                                        "/auth/verifying-key**",
                                        "/auth/login**")
                                .filters(
                                        filter -> filter.stripPrefix(1)
                                )
                                .uri(authUrl)
                )
                .build();
    }
}

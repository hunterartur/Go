package com.simbirsoft.gateway.route;

import com.simbirsoft.gateway.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileRoute {

    @Value("${application.services.profile.url}")
    private String profileUrl;

    @Bean
    public RouteLocator profileGatewayRoute(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route(
                        "profileGatewayRoute",
                        route -> route.path(
                                        "/profiles",
                                        "/profile/**",
                                        "/profile**",
                                        "/cities",
                                        "/city/**",
                                        "/city**")
                                .filters(
                                        filter -> filter.filter(authFilter)
                                )
                                .uri(profileUrl)
                )
                .build();
    }
}

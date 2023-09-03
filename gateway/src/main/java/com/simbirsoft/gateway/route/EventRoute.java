package com.simbirsoft.gateway.route;

import com.simbirsoft.gateway.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventRoute {

    @Value("${application.services.event.url}")
    private String eventUrl;

    @Bean
    public RouteLocator eventGatewayRoute(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route(
                        "eventGatewayRoute",
                        route -> route.path(
                                        "/events",
                                        "/event/**",
                                        "/event**",
                                        "/event-templates",
                                        "/event-template",
                                        "/event-template/**")
                                .filters(
                                        filter -> filter.filter(authFilter)
                                )
                                .uri(eventUrl)
                )
                .build();
    }
}

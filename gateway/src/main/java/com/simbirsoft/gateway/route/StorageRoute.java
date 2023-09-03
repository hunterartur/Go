package com.simbirsoft.gateway.route;

import com.simbirsoft.gateway.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageRoute {

    @Value("${application.services.image-storage.url}")
    private String imageStorageUrl;

    @Bean
    public RouteLocator storageLocator(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route(
                        "storageLocator",
                        route -> route.path(
                                        "/upload-image",
                                        "/get-image/**")
                                .filters(
                                        filter -> filter.filter(authFilter)
                                )
                                .uri(imageStorageUrl)
                )
                .build();
    }
}

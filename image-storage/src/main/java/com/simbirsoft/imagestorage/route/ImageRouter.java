package com.simbirsoft.imagestorage.route;

import com.simbirsoft.imagestorage.service.ImageStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ImageRouter {

    @Bean
    public RouterFunction<ServerResponse> imageRoutes(ImageStorageService imageStorageService) {
        return RouterFunctions.route()
                .POST("/upload-image", imageStorageService::uploadImage)
                .GET("/get-image/{id}", imageStorageService::getImageById)
                .build();
    }
}

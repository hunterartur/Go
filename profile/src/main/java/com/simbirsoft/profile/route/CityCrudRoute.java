package com.simbirsoft.profile.route;

import com.simbirsoft.profile.service.CrudService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityCrudRoute {

    public static final String CITIES_PATH = "/cities";
    public static final String CITY_PATH = "/city";

    @Bean
    public RouterFunction<ServerResponse> cityRoute(@Qualifier("cityCrudService") CrudService crudService) {
        return RouterFunctions.route()
                .GET(CITIES_PATH, crudService::getAll)
                .GET(CITY_PATH + "/ids", crudService::getAllId)
                .GET(CITY_PATH + "/{id}", crudService::getById)
                .POST(CITY_PATH, RequestPredicates.contentType(MediaType.APPLICATION_JSON), crudService::save)
                .PUT(CITY_PATH + "/{id}", RequestPredicates.contentType(MediaType.APPLICATION_JSON), crudService::update)
                .DELETE(CITY_PATH + "/{id}", crudService::delete)
                .build();
    }
}

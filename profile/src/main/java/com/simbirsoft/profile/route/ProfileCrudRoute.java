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
public class ProfileCrudRoute {

    private static final String PROFILE_PATH = "/profile";
    private static final String PROFILES_PATH = "/profiles";

    @Bean
    public RouterFunction<ServerResponse> profileRoute(
            @Qualifier("profileCrudService") CrudService profileCrudService,
            @Qualifier("userEventCrudService") CrudService userEventCrudService) {
        return RouterFunctions.route()
                .GET(PROFILES_PATH, profileCrudService::getAll)
                .GET(PROFILE_PATH + "/ids", profileCrudService::getAllId)
                .GET(PROFILE_PATH + "/{id}", profileCrudService::getById)
                .POST(PROFILE_PATH, RequestPredicates.contentType(MediaType.APPLICATION_JSON), profileCrudService::save)
                .PUT(PROFILE_PATH + "/{id}", RequestPredicates.contentType(MediaType.APPLICATION_JSON), profileCrudService::update)
                .DELETE(PROFILE_PATH + "/{id}", profileCrudService::delete)
                .POST(PROFILE_PATH + "/user-event", userEventCrudService::save)
                .build();
    }
}

package com.simbirsoft.mockauthserver.route;

import com.simbirsoft.mockauthserver.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class AuthorizationRoute {

    @Bean
    public RouterFunction<ServerResponse> authRoute(AuthService authService) {
        var routeLogin = RequestPredicates.POST("/login")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));
        var routeVerifiedKey = RequestPredicates.GET("/verifying-key");
        return RouterFunctions.route(routeLogin, authService::login)
                .andRoute(routeVerifiedKey, authService::getVerifiedKey);
    }
}

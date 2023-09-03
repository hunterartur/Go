package com.simbirsoft.eventaction.route;

import com.simbirsoft.eventaction.service.EventService;
import com.simbirsoft.eventaction.service.EventTemplateService;
import com.simbirsoft.eventaction.service.EventTemplateServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EventRoute {

    private static final String EVENTS_PATH = "/events";
    private static final String EVENT_PATH = "/event";

    @Bean
    public RouterFunction<ServerResponse> route(EventService eventService, EventTemplateServiceImpl eventTemplateService) {
        return RouterFunctions.route()
                .GET(EVENTS_PATH, eventService::getPagination)
                .GET(EVENT_PATH + "/{id}", eventService::getById)
                .POST(EVENT_PATH + "/validate", eventService::validate)
                .POST(EVENT_PATH, eventService::save)
                .POST("/event-template", eventTemplateService::save)
                .GET("/event-template/{id}", eventTemplateService::getById)
                .GET("/event-templates", eventTemplateService::getPagination)
                .build();
    }
}

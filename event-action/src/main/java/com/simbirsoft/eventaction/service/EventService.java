package com.simbirsoft.eventaction.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface EventService {

    Mono<ServerResponse> getById(ServerRequest serverRequest);

    Mono<ServerResponse> validate(ServerRequest serverRequest);

    Mono<ServerResponse> save(ServerRequest serverRequest);

    Mono<ServerResponse> getPagination(ServerRequest serverRequest);
}

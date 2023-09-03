package com.simbirsoft.profile.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface CrudService {

    Mono<ServerResponse> getAllId(ServerRequest serverResponse);


    Mono<ServerResponse> getAll(ServerRequest serverResponse);


    Mono<ServerResponse> getById(ServerRequest serverResponse);

    Mono<ServerResponse> save(ServerRequest serverRequest);

    Mono<ServerResponse> update(ServerRequest serverRequest);

    Mono<ServerResponse> delete(ServerRequest serverRequest);
}

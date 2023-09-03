package com.simbirsoft.imagestorage.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface StorageService {

    Mono<ServerResponse> uploadImage(ServerRequest serverRequest);

    Mono<ServerResponse> getImageById(ServerRequest serverRequest);
}

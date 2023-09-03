package com.simbirsoft.mockauthserver.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<ServerResponse> login(ServerRequest serverRequest);

    Mono<ServerResponse> getVerifiedKey(ServerRequest serverRequest);
}

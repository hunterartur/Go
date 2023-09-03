package com.simbirsoft.mockauthserver.service;

import com.simbirsoft.mockauthserver.Util.CoderBase64;
import com.simbirsoft.mockauthserver.Util.JwtUtil;
import com.simbirsoft.mockauthserver.dto.Request;
import com.simbirsoft.mockauthserver.dto.Response;
import com.simbirsoft.mockauthserver.property.JwtProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

    private final CrudService crudService;
    private final JwtUtil jwtUtil;

    private final JwtProperties jwtProperties;

    public AuthServiceImpl(@Qualifier("userCrudService") CrudService crudService, JwtUtil jwtUtil, JwtProperties jwtProperties) {
        this.crudService = crudService;
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .filterWhen(
                        req -> crudService.getUserFromEmail(req.username())
                                .filter(userRepo -> userRepo.password().equals(CoderBase64.encode(req.password())))
                                .hasElement())
                .switchIfEmpty(Mono.error(new RuntimeException("Нет такого пользователя или не найден!")))
                .flatMap(
                        req -> crudService.getUserFromEmail(req.username()))
                .map(
                        user -> new Response(jwtUtil.generateJwtToken(user.id(), user.email())))
                .flatMap(
                        response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response));
    }

    @Override
    public Mono<ServerResponse> getVerifiedKey(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(jwtProperties.secretKey()));
    }
}

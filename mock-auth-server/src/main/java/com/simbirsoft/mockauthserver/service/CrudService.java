package com.simbirsoft.mockauthserver.service;

import com.simbirsoft.mockauthserver.domain.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CrudService {

    Mono<User> getUserFromEmail(String email);

    Mono<User> getUserFromId(UUID id);

    Mono<Boolean> existsByEmail(String email);
}

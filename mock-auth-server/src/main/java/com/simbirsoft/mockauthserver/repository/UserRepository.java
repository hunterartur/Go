package com.simbirsoft.mockauthserver.repository;

import com.simbirsoft.mockauthserver.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);
}
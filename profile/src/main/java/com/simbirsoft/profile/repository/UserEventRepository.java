package com.simbirsoft.profile.repository;

import com.simbirsoft.profile.domain.UserEvent;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface UserEventRepository extends ReactiveCrudRepository<UserEvent, UUID> {

    @Query("SELECT id FROM user_events")
    Flux<UUID> getAllId();

    @Query("SELECT * FROM user_events WHERE id IN :ids")
    Flux<UserEvent> getAllByIds(List<UUID> ids);

    @Query("SELECT * FROM user_events")
    Flux<UserEvent> getAll();

    @Query("INSERT INTO user_events (id, action, event_id) VALUES (:#{#event.id}, :#{#event.action}, :#{#event.eventId})")
    Mono<Integer> persist(UserEvent event);

    @Query("SELECT * FROM user_events WHERE id = :id")
    Mono<UserEvent> getById(UUID id);
}

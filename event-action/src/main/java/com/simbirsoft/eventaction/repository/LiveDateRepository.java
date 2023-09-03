package com.simbirsoft.eventaction.repository;

import com.simbirsoft.eventaction.domain.LiveDate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LiveDateRepository extends ReactiveCrudRepository<LiveDate, UUID> {

    @Query("INSERT INTO live_date(id, start_event_date, registration_expired) VALUES (:#{#date.id}, :#{#date.startEventDate}, :#{#date.registrationExpired})")
    Mono<Integer> persist(LiveDate date);

    @Query("SELECT * FROM live_date WHERE id = :id")
    Mono<LiveDate> getById(UUID id);
}

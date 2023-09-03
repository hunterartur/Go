package com.simbirsoft.eventaction.repository;

import com.simbirsoft.eventaction.domain.EventTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EventTemplateRepository extends ReactiveCrudRepository<EventTemplate, UUID> {

    @Query("INSERT INTO event_templates(id, name, title, icon, image, event_type)" +
            " VALUES (:#{#eventTemplate.id}, :#{#eventTemplate.name}, :#{#eventTemplate.title}, :#{#eventTemplate.icon}, " +
            ":#{#eventTemplate.image}, :#{#eventTemplate.eventType})")
    Mono<Integer> persist(EventTemplate eventTemplate);

    @Query("SELECT * FROM event_templates WHERE id = :id")
    Mono<EventTemplate> getById(UUID id);

    Flux<EventTemplate> findAllBy(Pageable pageable);
}

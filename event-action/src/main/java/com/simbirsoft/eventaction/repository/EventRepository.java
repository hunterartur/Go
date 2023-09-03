package com.simbirsoft.eventaction.repository;

import com.simbirsoft.eventaction.domain.Event;
import com.simbirsoft.gocommonlib.enums.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EventRepository extends ReactiveCrudRepository<Event, UUID> {

    @Query("INSERT INTO event(id, title, text, image_url, event_type, author_id, members, live_date_id, city_id)" +
            " VALUES (:#{#event.id}, :#{#event.title}, :#{#event.text}, :#{#event.imageUrl}, :#{#event.eventType}, " +
            ":#{#event.author}, :#{#event.members}, :#{#event.liveDateId}, :#{#event.cityId})")
    Mono<Integer> persist(Event event);

    @Query("SELECT * FROM event WHERE id = :id")
    Mono<Event> getById(UUID id);

    @Query("SELECT * FROM event WHERE event_type = :eventType and city_id = :cityId")
    Flux<Event> getEventsByEventTypeAndCityId(EventType eventType, UUID cityId);

    Flux<Event> findAllBy(Pageable pageable);
}

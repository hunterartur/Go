package com.simbirsoft.profile.repository;

import com.simbirsoft.profile.domain.City;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CityRepository extends ReactiveCrudRepository<City, UUID> {

    @Query("SELECT id FROM cities")
    Flux<UUID> getAllId();

    @Query("SELECT * FROM cities")
    Flux<City> getAll();

    @Query("INSERT INTO cities (id, title) VALUES (:#{#city.id}, :#{#city.title})")
    Mono<Integer> persist(City city);

    @Query("SELECT * FROM cities WHERE id = :id")
    Mono<City> getById(UUID id);
}

package com.simbirsoft.profile.repository;

import com.simbirsoft.profile.domain.Profile;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, UUID> {

    @Query("SELECT id FROM profiles")
    Flux<UUID> getAllId();

    @Query("SELECT * FROM profiles")
    Flux<Profile> getAll();

    @Query("INSERT INTO profiles (id, first_name, last_name, email, image_url, city_id, event_ids) " +
            "VALUES (:#{#profile.id}, :#{#profile.firstName}, :#{#profile.lastName}, :#{#profile.email}, :#{#profile.imageUrl}, :#{#profile.cityId}, :#{#profile.eventIds})")
    Mono<Integer> persist(Profile profile);

    @Query("SELECT * FROM profiles WHERE id = :id")
    Mono<Profile> getById(UUID id);
}

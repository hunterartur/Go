package com.simbirsoft.imagestorage.repository;

import com.simbirsoft.imagestorage.domain.Image;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ImageRepository extends ReactiveCrudRepository<Image, UUID> {

    @Query("SELECT * FROM images WHERE id = :id")
    Mono<Image> getById(UUID id);

    @Query("INSERT INTO images(id, data) VALUES (:#{#imageEntity.id}, :#{#imageEntity.data})")
    Mono<Integer> persist(Image imageEntity);
}

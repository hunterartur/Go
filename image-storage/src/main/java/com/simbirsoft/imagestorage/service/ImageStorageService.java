package com.simbirsoft.imagestorage.service;

import com.simbirsoft.imagestorage.domain.Image;
import com.simbirsoft.imagestorage.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageStorageService implements StorageService {

    private final ImageRepository imageRepository;

    @Override
    public Mono<ServerResponse> uploadImage(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(String.class)
                .flatMap(s -> imageRepository.persist(new Image(UUID.randomUUID(), s)))
                .flatMap(integer -> ServerResponse.ok().build());
    }

    @Override
    public Mono<ServerResponse> getImageById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return imageRepository.findById(UUID.fromString(id))
                .flatMap(imageEntity -> ServerResponse.ok()
                        .bodyValue(imageEntity.data()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

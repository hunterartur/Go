package com.simbirsoft.profile.service;

import com.simbirsoft.gocommonlib.dto.CityDto;
import com.simbirsoft.gocommonlib.dto.ProfileDto;
import com.simbirsoft.profile.mapper.CityMapper;
import com.simbirsoft.profile.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityCrudService implements CrudService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public Mono<ServerResponse> getAllId(ServerRequest serverRequest) {
        return cityRepository.getAllId()
                .collectList()
                .flatMap(uuids ->
                        ServerResponse.ok().bodyValue(uuids))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return cityRepository.getAll()
                .map(cityMapper::toDto)
                .collectList()
                .flatMap(
                        cityDtos -> ServerResponse.ok()
                                .bodyValue(cityDtos))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        var id = UUID.fromString(serverRequest.pathVariable("id"));
        return cityRepository.getById(id)
                .map(cityMapper::toDto)
                .flatMap(
                        cityDto -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(cityDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        var cityDto = serverRequest.bodyToMono(CityDto.class);
        return cityDto.map(dto -> cityMapper.toEntity(UUID.randomUUID(), dto))
                .flatMap(cityRepository::persist)
                .flatMap(
                        integer -> ServerResponse.ok().build());
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var cityDto = serverRequest.bodyToMono(CityDto.class);
        var id = serverRequest.pathVariable("id");
        return cityDto.map(dto -> cityMapper.toEntity(UUID.fromString(id), dto))
                .flatMap(cityRepository::save)
                .flatMap(countUpdate -> ServerResponse.ok().bodyValue(countUpdate))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return cityRepository.getById(UUID.fromString(id))
                .flatMap(city -> ServerResponse.noContent().build(cityRepository.deleteById(UUID.fromString(id))))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

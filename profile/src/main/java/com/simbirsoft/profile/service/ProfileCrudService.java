package com.simbirsoft.profile.service;

import com.simbirsoft.gocommonlib.dto.ProfileDto;
import com.simbirsoft.profile.mapper.ProfileMapper;
import com.simbirsoft.profile.repository.CityRepository;
import com.simbirsoft.profile.repository.ProfileRepository;
import com.simbirsoft.profile.repository.UserEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileCrudService implements CrudService {

    private final ProfileRepository profileRepository;

    private final CityRepository cityRepository;

    private final ProfileMapper profileMapper;

    private final UserEventRepository eventRepository;

    @Override
    public Mono<ServerResponse> getAllId(ServerRequest serverRequest) {
        return profileRepository.getAllId()
                .collectList()
                .flatMap(uuids ->
                        ServerResponse.ok().bodyValue(uuids))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return profileRepository.findAll()
                .flatMap(
                        profile -> cityRepository.findById(profile.getCityId())
                                .flatMap(city -> eventRepository.getAllByIds(Arrays.asList(profile.getEventIds()))
                                        .collectList()
                                        .map(
                                                userEvents -> profileMapper.toDto(profile, city, userEvents))))
                .collectList()
                .flatMap(profileDto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(profileDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        var id = UUID.fromString(serverRequest.pathVariable("id"));
        return profileRepository.getById(id)
                .flatMap(
                        profile -> cityRepository.findById(profile.getCityId())
                                .flatMap(city -> eventRepository.getAllByIds(Arrays.asList(profile.getEventIds()))
                                        .collectList()
                                        .map(
                                                userEvents -> profileMapper.toDto(profile, city, userEvents))))
                .flatMap(
                        profileDto -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(profileDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        var profileDto = serverRequest.bodyToMono(ProfileDto.class);
        return profileDto.map(
                        dto -> profileMapper.toEntity(UUID.randomUUID(), dto))
                .flatMap(
                        profileRepository::persist)
                .flatMap(
                        profile -> ServerResponse.ok().build());
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var profileDto = serverRequest.bodyToMono(ProfileDto.class);
        var id = serverRequest.pathVariable("id");
        return profileDto.map(
                        dto -> profileMapper.toEntity(UUID.fromString(id), dto))
                .flatMap(profileRepository::save)
                .flatMap(
                        countUpdate -> ServerResponse.ok().bodyValue(countUpdate))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return profileRepository.getById(UUID.fromString(id))
                .flatMap(
                        profile -> ServerResponse.noContent().build(profileRepository.deleteById(UUID.fromString(id)))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

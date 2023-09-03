package com.simbirsoft.profile.service;

import com.simbirsoft.gocommonlib.constant.CommonConstant;
import com.simbirsoft.gocommonlib.dto.UserEventDto;
import com.simbirsoft.profile.mapper.UserEventMapper;
import com.simbirsoft.profile.repository.ProfileRepository;
import com.simbirsoft.profile.repository.UserEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserEventCrudService implements CrudService {

    private final UserEventRepository userEventRepository;
    private final UserEventMapper userEventMapper;
    private final ProfileRepository profileRepository;

    @Override
    public Mono<ServerResponse> getAllId(ServerRequest serverResponse) {
        throw new RuntimeException("Не реализовано");
    }

    @Override
    public Mono<ServerResponse> getAll(ServerRequest serverResponse) {
        throw new RuntimeException("Не реализовано");
    }

    @Override
    public Mono<ServerResponse> getById(ServerRequest serverResponse) {
        throw new RuntimeException("Не реализовано");
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        var userId = serverRequest.headers().header(CommonConstant.X_USER_ID).get(0);
        return serverRequest.bodyToMono(UserEventDto.class)
                .flatMap(
                        userEventDto -> profileRepository.getById(UUID.fromString(userId))
                                .flatMap(
                                        profile -> {
                                            if (profile != null && profile.getEventIds() != null) {
                                                List<UUID> list = new ArrayList<>(Arrays.stream(profile.getEventIds()).toList());
                                                list.add(UUID.fromString(userId));
                                                profile.setEventIds(list.toArray(UUID[]::new));
                                            } else {
                                                UUID[] eventIds = new UUID[1];
                                                eventIds[0] = UUID.fromString(userId);
                                                profile.setEventIds(eventIds);
                                            }
                                            return profileRepository.save(profile);
                                        })
                                .flatMap(
                                        profile -> userEventRepository.persist(userEventMapper.toEntity(UUID.randomUUID(), userEventDto))
                                ))
                .flatMap(
                        integer -> ServerResponse.ok().build());
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        throw new RuntimeException("Не реализовано");
    }

    @Override
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        throw new RuntimeException("Не реализовано");
    }
}

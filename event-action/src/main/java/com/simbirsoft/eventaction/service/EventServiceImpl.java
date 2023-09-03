package com.simbirsoft.eventaction.service;

import com.simbirsoft.eventaction.dto.EventPostDto;
import com.simbirsoft.eventaction.dto.LiveDateDto;
import com.simbirsoft.eventaction.dto.ValidateRequest;
import com.simbirsoft.eventaction.mapper.EventMapper;
import com.simbirsoft.eventaction.mapper.LiveDateMapper;
import com.simbirsoft.eventaction.repository.EventRepository;
import com.simbirsoft.eventaction.repository.LiveDateRepository;
import com.simbirsoft.gocommonlib.constant.CommonConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final LiveDateRepository dateRepository;

    private final EventMapper mapper;

    private final LiveDateMapper dateMapper;

    @Override
    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        var id = serverRequest.pathVariable("id");
        return eventRepository.getById(UUID.fromString(id))
                .flatMap(
                        event -> dateRepository.getById(event.getLiveDateId())
                                .map(
                                        liveDate -> mapper.toDto(event, liveDate)
                                ))
                .flatMap(
                        eventGetDto -> ServerResponse.ok().bodyValue(eventGetDto)
                ).switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> validate(ServerRequest serverRequest) {
        var validateRequestMono = serverRequest.bodyToMono(ValidateRequest.class);
        return validateRequestMono.flatMap(
                        validateRequest -> eventRepository
                                .getEventsByEventTypeAndCityId(validateRequest.eventType(), validateRequest.cityId())
                                .filterWhen(
                                        event -> dateRepository.getById(event.getLiveDateId())
                                                .map(
                                                        liveDate ->
                                                                validateEvent(dateMapper.toDto(liveDate), validateRequest.liveDateDto())))
                                .collectList())
                .flatMap(
                        listMono -> ServerResponse.ok().bodyValue(listMono))
                .switchIfEmpty(ServerResponse.ok().bodyValue(Collections.emptyList()));
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        var eventPostDtoMono = serverRequest.bodyToMono(EventPostDto.class);
        var authorId = serverRequest.headers().header(CommonConstant.X_USER_ID).get(0);
        return eventPostDtoMono.map(
                        dto -> mapper.saveToEntity(UUID.randomUUID(), UUID.fromString(authorId), dto))
                .flatMap(
                        event -> dateRepository.persist(event.getLiveDate())
                                .switchIfEmpty(eventRepository.persist(event)
                                )
                ).flatMap(event -> ServerResponse.ok().build());

    }

    @Override
    public Mono<ServerResponse> getPagination(ServerRequest serverRequest) {
        String page = serverRequest.queryParam("page").orElse("1");
        String size = serverRequest.queryParam("size").orElse("1");
        Pageable pageable = Pageable.ofSize(Integer.parseInt(size)).withPage(Integer.parseInt(page));
        return eventRepository.findAllBy(pageable)
                .collectList()
                .flatMap(
                        events -> ServerResponse.ok().bodyValue(events))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Валидация времени, если валидируемое значение меньще данного
     * и больше чем данное минус 30 мин, то true, иначе false
     *
     * @param source данное
     * @param valid  валидируемое значение
     * @return результат сравнения
     */
    private boolean validateEvent(LiveDateDto source, LiveDateDto valid) {
        return valid.registrationExpired() < source.registrationExpired()
                && valid.registrationExpired() > source.registrationExpired() - 30 * 60 * 1000;
    }
}

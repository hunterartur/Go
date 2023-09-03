package com.simbirsoft.eventaction.service;

import com.simbirsoft.eventaction.dto.EventTemplateDto;
import com.simbirsoft.eventaction.mapper.EventTemplateMapper;
import com.simbirsoft.eventaction.repository.EventTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventTemplateServiceImpl implements EventTemplateService {

    private final EventTemplateRepository eventTemplateRepository;
    private final EventTemplateMapper eventTemplateMapper;

    @Override
    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        UUID id = UUID.fromString(serverRequest.pathVariable("id"));
        return eventTemplateRepository.getById(id)
                .map(eventTemplateMapper::toDto)
                .flatMap(
                        eventTemplateDto -> ServerResponse.ok().bodyValue(eventTemplateDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(EventTemplateDto.class)
                .map(eventTemplateMapper::toEntity)
                .flatMap(eventTemplateRepository::persist)
                .flatMap(i -> ServerResponse.ok().build());
    }

    @Override
    public Mono<ServerResponse> getPagination(ServerRequest serverRequest) {
        String page = serverRequest.queryParam("page").orElse("1");
        String size = serverRequest.queryParam("size").orElse("1");
        Pageable pageable = Pageable.ofSize(Integer.parseInt(size)).withPage(Integer.parseInt(page));
        return eventTemplateRepository.findAllBy(pageable)
                .collectList()
                .flatMap(
                        eventTemplates -> ServerResponse.ok().bodyValue(eventTemplates))
                .switchIfEmpty(Mono.empty());
    }
}

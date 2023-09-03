package com.simbirsoft.eventaction.mapper;

import com.simbirsoft.eventaction.domain.Event;
import com.simbirsoft.eventaction.domain.LiveDate;
import com.simbirsoft.eventaction.dto.EventGetDto;
import com.simbirsoft.eventaction.dto.EventPostDto;
import com.simbirsoft.eventaction.dto.LiveDateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final LiveDateMapper liveDateMapper;

    public EventGetDto toDto(Event event) {
        return new EventGetDto(
                event.getId(),
                event.getTitle(),
                event.getText(),
                event.getImageUrl(),
                event.getEventType(),
                event.getAuthor(),
                List.of(event.getMembers()),
                liveDateMapper.toDto(event.getLiveDate()),
                event.getCityId()
        );
    }

    public EventGetDto toDto(Event event, LiveDate date) {
        return new EventGetDto(
                event.getId(),
                event.getTitle(),
                event.getText(),
                event.getImageUrl(),
                event.getEventType(),
                event.getAuthor(),
                List.of(event.getMembers()),
                liveDateMapper.toDto(date),
                event.getCityId()
        );
    }

    public Event saveToEntity(UUID id, UUID authorId, EventPostDto dto) {
        var liveDate = liveDateMapper.toEntity(UUID.randomUUID(), dto.date());
        return Event.builder()
                .withId(id)
                .withTitle(dto.title())
                .withText(dto.text())
                .withImageUrl(dto.imageUrl())
                .withEventType(dto.eventType())
                .withLiveDate(liveDate)
                .withLiveDateId(liveDate.getId())
                .withMembers(new UUID[]{authorId})
                .withAuthor(authorId)
                .withCityId(dto.cityId())
                .build();
    }
}

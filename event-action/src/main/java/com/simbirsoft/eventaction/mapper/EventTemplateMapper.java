package com.simbirsoft.eventaction.mapper;

import com.simbirsoft.eventaction.domain.EventTemplate;
import com.simbirsoft.eventaction.dto.EventTemplateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventTemplateMapper {

    public EventTemplateDto toDto(EventTemplate eventTemplate) {
        return new EventTemplateDto(
                eventTemplate.id(),
                eventTemplate.name(),
                eventTemplate.title(),
                eventTemplate.icon(),
                eventTemplate.image(),
                eventTemplate.eventType()
        );
    }

    public EventTemplate toEntity(EventTemplateDto eventTemplateDto) {
        return new EventTemplate(
                UUID.randomUUID(),
                eventTemplateDto.name(),
                eventTemplateDto.title(),
                eventTemplateDto.icon(),
                eventTemplateDto.image(),
                eventTemplateDto.eventType()
        );
    }
}

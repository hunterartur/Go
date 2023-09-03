package com.simbirsoft.profile.mapper;

import com.simbirsoft.gocommonlib.dto.UserEventDto;
import com.simbirsoft.profile.domain.UserEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserEventMapper {

    public UserEventDto toDto(UserEvent event) {
        return new UserEventDto(event.getAction(), event.getEventId());
    }

    public UserEvent toEntity(UUID id, UserEventDto userEventDto) {
        return new UserEvent(id, userEventDto.action(), userEventDto.eventId());
    }
}

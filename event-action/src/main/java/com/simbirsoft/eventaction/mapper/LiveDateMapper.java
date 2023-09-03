package com.simbirsoft.eventaction.mapper;

import com.simbirsoft.eventaction.domain.LiveDate;
import com.simbirsoft.eventaction.dto.LiveDateDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class LiveDateMapper {

    public LiveDateDto toDto(LiveDate liveDate) {
        return new LiveDateDto(
                liveDate.getStartEventDate().toEpochSecond(),
                liveDate.getRegistrationExpired().toEpochSecond());
    }

    public LiveDate toEntity(UUID id, LiveDateDto liveDate) {
        return new LiveDate(id,
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(liveDate.startEventDate() * 1000), ZoneId.systemDefault()),
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(liveDate.registrationExpired() * 1000), ZoneId.systemDefault()));
    }
}

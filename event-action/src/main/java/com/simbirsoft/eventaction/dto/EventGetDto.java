package com.simbirsoft.eventaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.gocommonlib.enums.EventType;

import java.util.List;
import java.util.UUID;

public record EventGetDto(
        UUID id,
        String title,
        String text,
        @JsonProperty("image")
        String imageUrl,
        EventType eventType,
        @JsonProperty("author")
        UUID authorId,
        List<UUID> members,
        @JsonProperty("date")
        LiveDateDto liveDate,
        @JsonProperty("city")
        UUID cityId
) {
}

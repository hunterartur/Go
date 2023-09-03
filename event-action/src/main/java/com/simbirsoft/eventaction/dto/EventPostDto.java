package com.simbirsoft.eventaction.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.simbirsoft.gocommonlib.enums.EventType;

import java.util.UUID;

public record EventPostDto(
        String title,
        String text,
        @JsonProperty("image")

        String imageUrl,
        EventType eventType,
        LiveDateDto date,
        @JsonProperty("city")
        UUID cityId
) {
}

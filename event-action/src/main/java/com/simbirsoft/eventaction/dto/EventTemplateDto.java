package com.simbirsoft.eventaction.dto;

import com.simbirsoft.gocommonlib.enums.EventType;

import java.util.UUID;

public record EventTemplateDto(
        UUID id,
        String name,
        String title,
        String icon,
        String image,
        EventType eventType) {
}

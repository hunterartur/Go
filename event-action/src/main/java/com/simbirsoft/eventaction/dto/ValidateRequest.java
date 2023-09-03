package com.simbirsoft.eventaction.dto;

import com.simbirsoft.gocommonlib.enums.EventType;

import java.util.UUID;

public record ValidateRequest(
        EventType eventType,
        LiveDateDto liveDateDto,
        UUID cityId
) {
}

package com.simbirsoft.gocommonlib.dto;

import com.simbirsoft.gocommonlib.enums.Action;

import java.util.UUID;

public record UserEventDto(Action action, UUID eventId) {
}

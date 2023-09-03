package com.simbirsoft.eventaction.dto;

import com.simbirsoft.gocommonlib.enums.Action;

import java.util.UUID;

public record AnswerOnEvent(
        Action action,
        UUID eventId
) {
}

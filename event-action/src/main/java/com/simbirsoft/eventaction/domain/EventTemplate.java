package com.simbirsoft.eventaction.domain;

import com.simbirsoft.gocommonlib.enums.EventType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("event_templates")
public record EventTemplate(
        @Id
        UUID id,
        String name,
        String title,
        String icon,
        String image,
        EventType eventType) {
}

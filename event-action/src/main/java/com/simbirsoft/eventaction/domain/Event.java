package com.simbirsoft.eventaction.domain;

import com.simbirsoft.gocommonlib.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table("event")
@Builder(setterPrefix = "with")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private UUID id;

    private String title;

    private String text;

    @Column("image_url")
    private String imageUrl;

    @Column("event_type")
    private EventType eventType;

    @Column("author_id")
    private UUID author;

    private UUID[] members;

    @Column("live_date_id")
    private UUID liveDateId;

    @Transient
    private LiveDate liveDate;

    @Column("city_id")
    private UUID cityId;
}

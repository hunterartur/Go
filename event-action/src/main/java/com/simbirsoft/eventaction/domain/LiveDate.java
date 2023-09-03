package com.simbirsoft.eventaction.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table("live_date")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiveDate {
    @Id
    private UUID id;
    @Column("start_event_date")
    private OffsetDateTime startEventDate;
    @Column("registration_expired")
    private OffsetDateTime registrationExpired;


}

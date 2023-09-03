package com.simbirsoft.profile.domain;

import com.simbirsoft.gocommonlib.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("user_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {

    @Id
    private UUID id;
    private Action action;
    @Column("event_id")
    private UUID eventId;
}

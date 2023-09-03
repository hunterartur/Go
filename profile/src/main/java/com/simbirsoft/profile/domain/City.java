package com.simbirsoft.profile.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("cities")
public record City(
        @Id
        UUID id,
        String title) {
}

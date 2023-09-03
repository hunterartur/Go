package com.simbirsoft.imagestorage.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("images")
public record Image(
        @Id
        UUID id,
        String data) {
}

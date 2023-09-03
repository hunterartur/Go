package com.simbirsoft.gocommonlib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@JsonRootName("user")
public record ProfileDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        @JsonProperty("image")
        String imageUrl,
        CityDto city,
        List<UserEventDto> userEvents
) {
}

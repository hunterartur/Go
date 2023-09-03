package com.simbirsoft.gocommonlib.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("city")
public record CityDto(String id, String title) {
}

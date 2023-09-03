package com.simbirsoft.profile.mapper;

import com.simbirsoft.gocommonlib.dto.CityDto;
import com.simbirsoft.profile.domain.City;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CityMapper {

    public CityDto toDto(City city) {
        return new CityDto(city.id().toString(), city.title());
    }

    public City toEntity(CityDto city) {
        return new City(UUID.fromString(city.id()), city.title());
    }

    public City toEntity(UUID id, CityDto city) {
        return new City(id, city.title());
    }
}

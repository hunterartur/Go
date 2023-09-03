package com.simbirsoft.profile.mapper;

import com.simbirsoft.gocommonlib.dto.ProfileDto;
import com.simbirsoft.profile.domain.City;
import com.simbirsoft.profile.domain.Profile;
import com.simbirsoft.profile.domain.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final CityMapper cityMapper;
    private final UserEventMapper userEventMapper;

    public ProfileDto toDto(Profile profile) {
        return new ProfileDto(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getEmail(),
                profile.getImageUrl(),
                cityMapper.toDto(profile.getCity()),
                profile.getUserEvents().stream().map(userEventMapper::toDto).toList()
        );
    }

    public ProfileDto toDto(Profile profile, City city, List<UserEvent> userEvents) {
        return new ProfileDto(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getEmail(),
                profile.getImageUrl(),
                cityMapper.toDto(city),
                userEvents.stream().map(userEventMapper::toDto).toList()
        );
    }

    public Profile toEntity(UUID id, ProfileDto profileDto) {
        List<UUID> eventIds = new ArrayList<>();
        List<UserEvent> userEvents = profileDto.userEvents().stream().map(dto -> {
            UUID evId = UUID.randomUUID();
            eventIds.add(evId);
            return userEventMapper.toEntity(evId, dto);
        }).toList();
        return new Profile(
                id,
                profileDto.firstName(),
                profileDto.lastName(),
                profileDto.email(),
                profileDto.imageUrl(),
                UUID.fromString(profileDto.city().id()),
                cityMapper.toEntity(profileDto.city()),
                eventIds.toArray(UUID[]::new),
                userEvents);
    }
}

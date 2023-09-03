package com.simbirsoft.mockauthserver.service;

import com.simbirsoft.mockauthserver.domain.User;
import com.simbirsoft.mockauthserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCrudService implements CrudService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUserFromEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Mono<User> getUserFromId(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

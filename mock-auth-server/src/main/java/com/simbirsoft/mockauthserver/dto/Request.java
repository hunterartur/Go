package com.simbirsoft.mockauthserver.dto;

public record Request(
        String username,
        String password
) {
}

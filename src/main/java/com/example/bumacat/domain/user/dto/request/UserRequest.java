package com.example.bumacat.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserRequest (
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String password
) {
}

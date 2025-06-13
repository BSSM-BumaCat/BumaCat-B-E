package com.example.bumacat.domain.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserRequest (
        @NotNull
        String email,
        @NotNull
        String password
) {
}

package com.example.bumacat.global.security.dto;

public record LoginRequest (
        String loginId,
        String password
) {
}

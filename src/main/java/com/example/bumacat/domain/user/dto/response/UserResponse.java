package com.example.bumacat.domain.user.dto.response;

public record UserResponse (
        Long userId,
        String name,
        String loginId
) {

}

package com.example.bumacat.domain.auth.model.vo;

import lombok.Builder;

@Builder
public record TokenSet (
        String accessToken,
        String refreshToken
) {
  public static TokenSet of(String accessToken, String refreshToken) {
    return TokenSet.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }
}

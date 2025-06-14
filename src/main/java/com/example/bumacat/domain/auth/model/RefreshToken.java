package com.example.bumacat.domain.auth.model;

import com.example.bumacat.domain.user.model.type.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24)
@Builder
@Getter
public class RefreshToken {
  @Id
  private String refreshToken;
  private String email;
  private String role;

  public static RefreshToken of(String token, String email, String role) {
    return RefreshToken.builder()
            .refreshToken(token)
            .email(email)
            .role(role)
            .build();
  }
}

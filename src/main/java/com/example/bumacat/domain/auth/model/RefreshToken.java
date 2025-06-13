package com.example.bumacat.domain.auth.model;

import com.example.bumacat.domain.user.model.type.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Builder
@Getter
public class RefreshToken {
  @Id
  private String id;
  private String refreshToken;
  private String email;
  private String role;

  public static RefreshToken of(String id, String token, String email, String role) {
    return RefreshToken.builder()
            .id(id)
            .refreshToken(token)
            .email(email)
            .role(role)
            .build();
  }

  public void update(String newRefreshToken) {
    this.refreshToken = newRefreshToken;
  }
}

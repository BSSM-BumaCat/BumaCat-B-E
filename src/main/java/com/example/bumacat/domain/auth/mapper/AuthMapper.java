package com.example.bumacat.domain.auth.mapper;

import com.example.bumacat.domain.auth.model.RefreshToken;
import com.example.bumacat.domain.auth.model.vo.TokenSet;
import com.example.bumacat.domain.user.model.type.UserRole;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthMapper {

  public RefreshToken toRefreshToken(String token, String email, String role) {
    return RefreshToken.of(token, email, role);
  }

  public TokenSet toTokenSet(String accessToken, String refreshToken) {
    return TokenSet.of(accessToken, refreshToken);
  }
}

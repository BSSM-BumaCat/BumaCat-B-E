package com.example.bumacat.domain.auth.service;

import com.example.bumacat.domain.auth.exception.NotFoundRefreshTokenException;
import com.example.bumacat.domain.auth.mapper.AuthMapper;
import com.example.bumacat.domain.auth.model.RefreshToken;
import com.example.bumacat.domain.auth.model.vo.TokenSet;
import com.example.bumacat.domain.auth.repository.RefreshTokenRepository;
import com.example.bumacat.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final AuthMapper authMapper;

  public TokenSet reissue(String token) {
    RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
            .orElseThrow(NotFoundRefreshTokenException::getInstance);

    String email = refreshToken.getEmail();
    String role = refreshToken.getRole();
    String newAccessToken = jwtProvider.createAccessToken(email, role);
    String newRefreshToken = jwtProvider.createRefreshToken(email, role);
    TokenSet tokenSet = authMapper.toTokenSet(newAccessToken, newRefreshToken);

    refreshToken.update(newRefreshToken);
    refreshTokenRepository.save(refreshToken);

    return tokenSet;

  }
}

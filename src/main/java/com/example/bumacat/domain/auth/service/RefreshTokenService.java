package com.example.bumacat.domain.auth.service;

import com.example.bumacat.domain.auth.mapper.AuthMapper;
import com.example.bumacat.domain.auth.model.RefreshToken;
import com.example.bumacat.domain.auth.repository.RefreshTokenRepository;
import com.example.bumacat.domain.user.model.type.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final AuthMapper authMapper;

  public void storeRefreshToken(String token, String email, String role) {
    RefreshToken refreshToken = authMapper.toRefreshT
package com.example.bumacat.global.security.jwt;

import com.example.bumacat.global.config.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
  private final JwtProperties jwtProperties;

  private final static String ACCESS_TOKEN = "access_token";
  private final static String REFRESH_TOKEN = "refresh_token";

  public String createAccessToken(Long userId, String role) {
    String token = createToken(userId, role, ACCESS_TOKEN, jwtProperties.getAccessExp());
    return token;
  }

  public String createRefreshToken(Long userId, String role) {
    String token = createToken(userId, role, REFRESH_TOKEN, jwtProperties.getRefreshExp());
    return token;
  }

  private String createToken(Long userId, String role, String accessToken, Long exp) {
    Date now = new Date();
    return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .setSubject(String.valueOf(userId))
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + exp))
            .compact();
  }


}

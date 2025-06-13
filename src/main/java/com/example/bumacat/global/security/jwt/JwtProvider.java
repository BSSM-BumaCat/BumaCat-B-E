package com.example.bumacat.global.security.jwt;

import com.example.bumacat.global.config.properties.JwtProperties;
import com.example.bumacat.global.security.auth.AuthDetails;
import com.example.bumacat.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
  private final JwtProperties jwtProperties;
  private final AuthDetailsService authDetailsService;

  private final static String ACCESS_TOKEN = "access_token";
  private final static String REFRESH_TOKEN = "refresh_token";

  public String createAccessToken(String email, String role) {
    String token = createToken(email, role, ACCESS_TOKEN, jwtProperties.getAccessExp());
    return token;
  }

  public String createRefreshToken(String email, String role) {
    String token = createToken(email, role, REFRESH_TOKEN, jwtProperties.getRefreshExp());
    return token;
  }

  private String createToken(String email, String role, String accessToken, Long exp) {
    Date now = new Date();
    return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .setSubject(email)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + exp))
            .compact();
  }

  public String parseToken(HttpServletRequest request) {
    String token = request.getHeader(jwtProperties.getHeader());
    if (token == null) return null;
    return resolveToken(token);
  }

  private String resolveToken(String token) {
    if (token != null && token.startsWith(jwtProperties.getPrefix())) {
      return token.replace(jwtProperties.getPrefix(), "");
    }
    return null;
  }

  public Authentication getAuthentication(String parseToken) {
    String email = getSubject(parseToken);
    UserDetails userDetails = authDetailsService.loadUserByUsername(email);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getSubject(String parseToken) {
    Claims claims = parseToken(parseToken);
    return claims.getSubject();
  }

  private Claims parseToken(String parseToken) {
    return Jwts.parserBuilder()
            .setSigningKey(jwtProperties.getSecretKey())
            .build()
            .parseClaimsJws(parseToken)
            .getBody();
  }
}

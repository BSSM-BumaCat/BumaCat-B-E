package com.example.bumacat.global.security.filter;


import com.example.bumacat.global.security.auth.AuthDetails;
import com.example.bumacat.global.security.dto.LoginRequest;
import com.example.bumacat.global.security.exception.FailedLoginException;
import com.example.bumacat.global.security.jwt.JwtProvider;
import com.example.bumacat.global.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
  private final JwtProvider jwtProvider;
  private final ObjectMapper objectMapper;
  private final AuthenticationManager authenticationManager;

  public LoginFilter(JwtProvider jwtProvider, ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
    this.jwtProvider = jwtProvider;
    this.objectMapper = objectMapper;
    this.authenticationManager = authenticationManager;
    setFilterProcessesUrl("/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    LoginRequest loginRequest = parseLoginRequest(request);
    String loginId = loginRequest.loginId();
    String password = loginRequest.password();

    UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginId, password);
    this.setDetails(request, authRequest);
    return authenticationManager.authenticate(authRequest);
  }

  private LoginRequest parseLoginRequest(HttpServletRequest request) {
    try (InputStream inputStream = request.getInputStream()) {
      return objectMapper.readValue(inputStream, LoginRequest.class);
    } catch (IOException e) {
      throw FailedLoginException.getInstance();
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
    log.info("login success");
    AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();

    String accessToken = jwtProvider.createAccessToken(authDetails.getUsername(), authDetails.getAuthorities().toString());
    response.addHeader("accessToken", accessToken);

    String refreshToken = jwtProvider.createRefreshToken(authDetails.getUsername(), authDetails.getAuthorities().toString());
    response.addCookie(HttpUtil.bakeCookie("refreshToken", refreshToken));
  }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
      log.error("login failed");
    }
}

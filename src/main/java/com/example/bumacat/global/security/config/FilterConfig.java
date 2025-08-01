package com.example.bumacat.global.security.config;

import com.example.bumacat.global.security.filter.JwtFilter;
import com.example.bumacat.global.security.filter.LoginFilter;
import com.example.bumacat.global.security.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtProvider jwtProvider;
  private final ObjectMapper objectMapper;
  private final AuthenticationManager authenticationManager;

  @Override
  public void configure(HttpSecurity http) {
    http
            .addFilterAt(new LoginFilter(jwtProvider, objectMapper, authenticationManager), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JwtFilter(jwtProvider), LoginFilter.class);

  }

  public HttpSecurity build() {
    return getBuilder();
  }
}

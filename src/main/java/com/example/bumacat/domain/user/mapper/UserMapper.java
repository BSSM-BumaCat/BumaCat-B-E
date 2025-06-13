package com.example.bumacat.domain.user.mapper;

import com.example.bumacat.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final PasswordEncoder passwordEncoder;

  public User toUser(String email, String password) {
    String encodedPassword = passwordEncoder.encode(password);
    return User.of(email, encodedPassword);
  }
}

package com.example.bumacat.domain.user.mapper;

import com.example.bumacat.domain.user.dto.response.UserResponse;
import com.example.bumacat.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final PasswordEncoder passwordEncoder;

  public User toUser(String name ,String email, String password) {
    String encodedPassword = passwordEncoder.encode(password);
    return User.of(name, email, encodedPassword);
  }

  public UserResponse toUserResponse(User user) {
    return new UserResponse(user.getUserId(), user.getName(), user.getEmail());
  }
}

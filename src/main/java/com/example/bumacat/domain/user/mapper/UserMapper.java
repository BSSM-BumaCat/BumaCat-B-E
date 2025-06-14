package com.example.bumacat.domain.user.mapper;

import com.example.bumacat.domain.user.dto.request.UserRequest;
import com.example.bumacat.domain.user.dto.response.UserResponse;
import com.example.bumacat.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  private final PasswordEncoder passwordEncoder;

  public User toUser(UserRequest userRequest) {
    String name = userRequest.name();
    String loginId = userRequest.loginId();
    String password = userRequest.password();

    String encodedPassword = passwordEncoder.encode(password);
    return User.of(name, loginId, encodedPassword);
  }

  public UserResponse toUserResponse(User user) {
    return new UserResponse(user.getUserId(), user.getName(), user.getLoginId());
  }
}

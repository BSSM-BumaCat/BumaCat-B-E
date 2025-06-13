package com.example.bumacat.domain.user.service;

import com.example.bumacat.domain.user.dto.UserRequest;
import com.example.bumacat.domain.user.mapper.UserMapper;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public void register(UserRequest userRequest) {
    String email = userRequest.email();
    String password = userRequest.password();
    User user = userMapper.toUser(email, password);
    userRepository.save(user);
  }
}

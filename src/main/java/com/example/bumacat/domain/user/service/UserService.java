package com.example.bumacat.domain.user.service;

import com.example.bumacat.domain.user.dto.request.UserRequest;
import com.example.bumacat.domain.user.dto.response.UserResponse;
import com.example.bumacat.domain.user.mapper.UserMapper;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.domain.user.repository.UserRepository;
import com.example.bumacat.global.dto.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Transactional
  public void register(UserRequest userRequest) {
    User user = userMapper.toUser(userRequest);
    userRepository.save(user);
  }

  @Transactional
  public void leave(Long userId) {
    userRepository.deleteById(userId);
  }

  public CursorPage<UserResponse> findSeller(Long cursorId, int size) {
    Pageable pageable = PageRequest.of(0, size);
    Slice<User> userSlice = cursorId == null
            ? userRepository.findSellers(pageable)
            : userRepository.findSellersByCursorId(cursorId, pageable);

    List<UserResponse> userResponsesList = userSlice.getContent()
            .stream()
            .map(userMapper::toUserResponse)
            .toList();

    return new CursorPage<>(userSlice.hasNext(), userResponsesList);
  }

  public UserResponse findMe(User user) {
    UserResponse userResponse = userMapper.toUserResponse(user);
    return userResponse;
  }
}

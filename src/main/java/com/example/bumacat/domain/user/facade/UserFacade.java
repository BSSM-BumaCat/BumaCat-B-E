package com.example.bumacat.domain.user.facade;

import com.example.bumacat.domain.user.exception.NotFoundUserException;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.domain.user.repository.UserRepository;
import com.example.bumacat.global.security.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
  private final UserRepository userRepository;

  public User getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
    String loginId = authDetails.getUsername();
    User user = userRepository.findByLoginId(loginId)
            .orElseThrow(NotFoundUserException::getInstance);
    return user;
  }
}

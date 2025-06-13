package com.example.bumacat.global.security.auth;

import com.example.bumacat.domain.user.exception.NotFoundUserException;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
            .orElseThrow(NotFoundUserException::getInstance);
    return new AuthDetails(user);
  }
}

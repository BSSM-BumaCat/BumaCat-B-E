package com.example.bumacat.domain.user.controller;

import com.example.bumacat.domain.user.dto.UserRequest;
import com.example.bumacat.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ResponseDto<Void>> register(@Valid @RequestBody UserRequest userRequest) {
    userService.register(userRequest);
  }
}

package com.example.bumacat.domain.user.controller;

import com.example.bumacat.domain.user.dto.request.UserRequest;
import com.example.bumacat.domain.user.dto.response.UserResponse;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.domain.user.service.UserService;
import com.example.bumacat.global.dto.CursorPage;
import com.example.bumacat.global.dto.ResponseDto;
import com.example.bumacat.global.util.HttpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @PostMapping
  public ResponseEntity<ResponseDto<Void>> register(@Valid @RequestBody UserRequest userRequest) {
    userService.register(userRequest);
    ResponseDto<Void> responseDto = HttpUtil.success("seller registered");
    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping("/{user-id}")
  public ResponseEntity<ResponseDto<Void>> leave(@PathVariable("user-id") Long userId) {
    userService.leave(userId);
    ResponseDto<Void> responseDto = HttpUtil.success("seller leaved");
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<CursorPage<UserResponse>>> find(@RequestParam(value = "cursor-id", required = false) Long cursorId, @RequestParam("size")  int size) {
    CursorPage<UserResponse> userResponseList = userService.findSeller(cursorId, size);
    ResponseDto<CursorPage<UserResponse>> responseDto = HttpUtil.success("find sellers", userResponseList);
    return ResponseEntity.ok(responseDto);
  }

  @GetMapping
  public ResponseEntity<ResponseDto<UserResponse>> findMe(User user) {
    UserResponse userResponse = userService.findMe(user);
    ResponseDto<UserResponse> responseDto = HttpUtil.success("find me", userResponse);
    return ResponseEntity.ok(responseDto);
  }

}

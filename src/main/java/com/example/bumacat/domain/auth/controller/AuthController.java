package com.example.bumacat.domain.auth.controller;

import com.example.bumacat.domain.auth.dto.response.AccessTokenResponse;
import com.example.bumacat.domain.auth.model.vo.TokenSet;
import com.example.bumacat.domain.auth.service.AuthService;
import com.example.bumacat.global.dto.ResponseDto;
import com.example.bumacat.global.util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/reissue")
  public ResponseEntity<ResponseDto<AccessTokenResponse>> reissue(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = HttpUtil.parseCookie("refreshToken", request.getCookies());
    TokenSet tokenSet = authService.reissue(refreshToken);

    response.addCookie(HttpUtil.bakeCookie("refreshToken", tokenSet.refreshToken()));
    ResponseDto<AccessTokenResponse> responseDto = HttpUtil.success(
            "access token reissued",
            new AccessTokenResponse(tokenSet.accessToken())
    );
    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping("/logout")
  public ResponseEntity<ResponseDto<Void>> logout(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = HttpUtil.parseCookie("refreshToken", request.getCookies());
    authService.logout(refreshToken);
    response.addCookie(HttpUtil.bakeExpiredCookie("refreshToken"));

    ResponseDto<Void> responseDto = HttpUtil.success("logout success");
    return ResponseEntity.ok(responseDto);
  }

}


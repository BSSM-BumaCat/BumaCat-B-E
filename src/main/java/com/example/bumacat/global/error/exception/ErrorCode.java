package com.example.bumacat.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND(404, "not found user"),
  REFRESH_TOKEN_NOT_FOUND(404, "not found refresh token"),
  COOKIE_NO(404,"no cookie"),
  LOGIN_FAILED(400, "login failed by tIncorrect format");
  private int statusCode;
  private String message;
}

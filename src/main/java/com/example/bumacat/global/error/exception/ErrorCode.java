package com.example.bumacat.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND(404, "not found user")
  ;
  private int statusCode;
  private String message;
}

package com.example.bumacat.global.security.exception;

import com.example.bumacat.global.error.exception.BaseException;
import com.example.bumacat.global.error.exception.ErrorCode;

public class FailedLoginException extends RuntimeException {
  public FailedLoginException() {
    super("Failed to login");
  }
  static class Holder {
    static final FailedLoginException INSTANCE = new FailedLoginException();
  }
  public static FailedLoginException getInstance() {
    return Holder.INSTANCE;
  }
}

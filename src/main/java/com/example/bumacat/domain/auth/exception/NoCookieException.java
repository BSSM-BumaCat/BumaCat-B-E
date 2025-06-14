package com.example.bumacat.domain.auth.exception;

import com.example.bumacat.global.error.exception.BaseException;
import com.example.bumacat.global.error.exception.ErrorCode;

public class NoCookieException extends BaseException {
  public NoCookieException() {
    super(ErrorCode.COOKIE_NO);
  }
  static class Holder {
    static final NoCookieException INSTANCE = new NoCookieException();
  }
  public static NoCookieException getInstance() {
    return Holder.INSTANCE;
  }
}

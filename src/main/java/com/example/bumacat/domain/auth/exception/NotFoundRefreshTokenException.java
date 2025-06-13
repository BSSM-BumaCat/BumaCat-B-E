package com.example.bumacat.domain.auth.exception;

import com.example.bumacat.global.error.exception.BaseException;
import com.example.bumacat.global.error.exception.ErrorCode;

public class NotFoundRefreshTokenException extends BaseException {
  public NotFoundRefreshTokenException() {
    super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
  }
  static class Holder {
    static final NotFoundRefreshTokenException INSTANCE = new NotFoundRefreshTokenException();
  }
  public static NotFoundRefreshTokenException getInstance() {
    return Holder.INSTANCE;
  }
}

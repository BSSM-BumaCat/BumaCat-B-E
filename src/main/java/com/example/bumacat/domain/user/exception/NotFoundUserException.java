package com.example.bumacat.domain.user.exception;

import com.example.bumacat.global.error.exception.BaseException;
import com.example.bumacat.global.error.exception.ErrorCode;

public class NotFoundUserException extends BaseException {

  public NotFoundUserException() {
    super(ErrorCode.USER_NOT_FOUND);
  }

  static class Holder {
    static final NotFoundUserException INSTANCE = new NotFoundUserException();
  }

  public static NotFoundUserException getInstance() {
    return Holder.INSTANCE;
  }

}

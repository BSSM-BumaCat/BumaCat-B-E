package com.example.bumacat.domain.market.exception;

import com.example.bumacat.global.error.exception.BaseException;
import com.example.bumacat.global.error.exception.ErrorCode;

public class NotFoundMarketException extends BaseException {
  public NotFoundMarketException() {
    super(ErrorCode.MARKET_NOT_FOUND);
  }
  static class Holder {
    static final NotFoundMarketException INSTANCE = new NotFoundMarketException();
  }
  public static NotFoundMarketException getInstance() {
    return Holder.INSTANCE;
  }
}

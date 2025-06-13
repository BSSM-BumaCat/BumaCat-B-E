package com.example.bumacat.global.error;

import com.example.bumacat.global.error.exception.BaseException;
import com.example.bumacat.global.error.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler
  public void baseExceptionHandler(BaseException e) {
    ErrorCode errorCode = e.getErrorCode();
    log.error("에러 코드: {}", errorCode);
    log.error("에러 메시지: {}", e.getMessage());
  }
}

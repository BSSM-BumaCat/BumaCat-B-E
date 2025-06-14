package com.example.bumacat.global.error;

import com.example.bumacat.global.dto.ResponseDto;
import com.example.bumacat.global.error.exception.BaseException;
import com.example.bumacat.global.error.exception.ErrorCode;
import com.example.bumacat.global.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ResponseDto<String>> baseExceptionHandler(BaseException e) {
    ErrorCode errorCode = e.getErrorCode();
    int statusCode = errorCode.getStatusCode();
    String message = errorCode.getMessage();

    log.error("에러 코드: {}", statusCode);
    log.error("에러 메시지: {}", message);

    ResponseDto<String> responseDto = HttpUtil.fail(message);
    return ResponseEntity
            .status(statusCode)
            .body(responseDto);
  }
}

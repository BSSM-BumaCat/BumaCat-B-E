package com.example.bumacat.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
        private ErrorCode errorCode;
}

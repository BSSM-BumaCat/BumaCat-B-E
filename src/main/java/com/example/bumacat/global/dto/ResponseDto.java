package com.example.bumacat.global.dto;

public record ResponseDto<T> (
        String message,
        T data
) {

}

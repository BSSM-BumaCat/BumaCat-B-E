package com.example.bumacat.global.util;

import com.example.bumacat.global.dto.ResponseDto;
import jakarta.servlet.http.Cookie;

public class HttpUtil {
  public static Cookie bakeCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setHttpOnly(true);
//    cookie.setSecure(true);
    cookie.setMaxAge(60 * 60 * 24 * 365);
    return cookie;
  }

  public static <T> ResponseDto<T> success(String message) {
    return new ResponseDto<>(message, null);
  }

  public static <T> ResponseDto<T> success(String message, T data) {
    return new ResponseDto<>(message, data);
  }

  public static String parseCookie(String key, Cookie[] cookies) {
    for(Cookie cookie : cookies) {
      if (key.equals(cookie.getName())) {
        return cookie.getValue();
      }
    }
    return null;
  }
}

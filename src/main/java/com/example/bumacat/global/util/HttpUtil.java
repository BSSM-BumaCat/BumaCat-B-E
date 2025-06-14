package com.example.bumacat.global.util;

import com.example.bumacat.domain.auth.exception.NoCookieException;
import com.example.bumacat.global.dto.ResponseDto;
import jakarta.servlet.http.Cookie;

public class HttpUtil {
  public static Cookie bakeCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setHttpOnly(true);
//    cookie.setSecure(true);
    cookie.setPath("/");
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
    if (cookies == null) {
      throw NoCookieException.getInstance();
    }
    for(Cookie cookie : cookies) {
      if (key.equals(cookie.getName())) {
        return cookie.getValue();
      }
    }
    return null;
  }

  public static Cookie bakeExpiredCookie(String refreshToken) {
    Cookie cookie = new Cookie(refreshToken, null);
    cookie.setMaxAge(0);
    return cookie;
  }

  public static <T> ResponseDto<T> fail(String message) {
    return new ResponseDto<>(message, null);
  }
}

package com.example.bumacat.global.util;

import jakarta.servlet.http.Cookie;

public class HttpUtil {
  public static Cookie bakeCookie(String key, String value) {
    Cookie cookie = new Cookie(key, value);
    cookie.setHttpOnly(true);
//    cookie.setSecure(true);
    cookie.setMaxAge(60 * 60 * 24 * 365);
    return cookie;
  }
}

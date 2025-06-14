package com.example.bumacat.global.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {
  private String secretKey;
  private Long accessExp;
  private Long refreshExp;
  private String prefix;
  private String header;
}

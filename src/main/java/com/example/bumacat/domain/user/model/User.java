package com.example.bumacat.domain.user.model;

import com.example.bumacat.domain.user.model.type.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String name;
  private String loginId;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserRole role;
  @CreatedDate
  private LocalDateTime createdAt;

  public static User of(String name, String loginId, String password) {
    return User.builder()
            .name(name)
            .loginId(loginId)
            .password(password)
            .role(UserRole.ROLE_SELLER)
            .build();
  }

}

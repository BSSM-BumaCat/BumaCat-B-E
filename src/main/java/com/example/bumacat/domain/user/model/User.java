package com.example.bumacat.domain.user.model;

import com.example.bumacat.domain.user.model.type.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String email;
  private String password;
  @Enumerated(EnumType.STRING)
  private UserRole role;
  @CreatedDate
  private LocalDateTime createdAt;
}

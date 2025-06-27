package com.example.bumacat.domain.market.model;

import com.example.bumacat.domain.market.dto.request.MarketRequest;
import com.example.bumacat.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Market {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long marketId;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  private String title;
  private String content;
  private LocalDateTime scheduled_open_at;
  @CreatedDate
  private LocalDateTime createdAt;

  public void modify(MarketRequest marketRequest, User user) {
    this.title = marketRequest.title();
    this.content = marketRequest.content();
    this.user = user;
    this.scheduled_open_at = marketRequest.scheduled_open_at();
  }
}

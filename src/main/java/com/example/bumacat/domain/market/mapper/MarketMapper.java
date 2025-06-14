package com.example.bumacat.domain.market.mapper;

import com.example.bumacat.domain.market.dto.request.MarketRequest;
import com.example.bumacat.domain.market.dto.response.MarketResponse;
import com.example.bumacat.domain.market.model.Market;
import com.example.bumacat.domain.user.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MarketMapper {
  public Market toMarket(MarketRequest marketRequest, User user) {
    String title = marketRequest.title();
    String content = marketRequest.content();
    LocalDateTime scheduled_open_at = marketRequest.scheduled_open_at();
    return Market.builder()
            .title(title)
            .content(content)
            .user(user)
            .scheduled_open_at(scheduled_open_at)
            .build();
  }

  public MarketResponse toMarketResponse(Market market) {
    Long marketId = market.getMarketId();
    String title = market.getTitle();
    String content = market.getContent();
    LocalDateTime scheduled_open_at = market.getScheduled_open_at();

    return MarketResponse.builder()
            .marketId(marketId)
            .title(title)
            .content(content)
            .scheduled_open_at(scheduled_open_at)
            .build();
  }
}

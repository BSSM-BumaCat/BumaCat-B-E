package com.example.bumacat.domain.market.service;

import com.example.bumacat.domain.market.dto.request.MarketRequest;
import com.example.bumacat.domain.market.dto.response.MarketResponse;
import com.example.bumacat.domain.market.exception.NotFoundMarketException;
import com.example.bumacat.domain.market.mapper.MarketMapper;
import com.example.bumacat.domain.market.model.Market;
import com.example.bumacat.domain.market.repository.MarketRepository;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.global.dto.CursorPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {
  private final MarketRepository marketRepository;
  private final MarketMapper marketMapper;

  @Transactional
  public void open(MarketRequest marketRequest, User user) {
    Market market = marketMapper.toMarket(marketRequest, user);
    marketRepository.save(market);
  }

  @Transactional
  public void modify(MarketRequest marketRequest, User user, Long marketId) {
    Market market = marketRepository.findById(marketId)
            .orElseThrow(NotFoundMarketException::getInstance);
    market.modify(marketRequest, user);
  }

  public MarketResponse findNextUpcomingMarket() {
    LocalDateTime now = LocalDateTime.now();

    Market market = marketRepository.findNextUpcomingMarket(now)
            .orElseThrow(NotFoundMarketException::getInstance);
    MarketResponse marketResponse = marketMapper.toMarketResponse(market);
    return marketResponse;
  }

  @Transactional
  public void delete(Long marketId) {
    marketRepository.deleteById(marketId);
  }

  public CursorPage<MarketResponse> findHistory(Long cursorId, int size) {
    Pageable pageable = PageRequest.of(0, size);

    Slice<Market> marketSlice = cursorId == null
            ? marketRepository.findHistory(pageable)
            : marketRepository.findHistoryByCursorId(cursorId, pageable);

    List<MarketResponse> marketResponsesList = marketSlice.getContent()
            .stream()
            .map(marketMapper::toMarketResponse)
            .toList();

    return new CursorPage<>(marketSlice.hasNext(), marketResponsesList);
  }
}

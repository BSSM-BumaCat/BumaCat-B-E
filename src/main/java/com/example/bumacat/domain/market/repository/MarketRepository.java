package com.example.bumacat.domain.market.repository;

import com.example.bumacat.domain.market.model.Market;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

  @Query("SELECT m FROM Market m WHERE m.scheduled_open_at >= :now ORDER BY m.scheduled_open_at ASC")
  Optional<Market> findNextUpcomingMarket(@Param("now") LocalDateTime now);

  @Query("SELECT m FROM Market m WHERE m.marketId <= :cursorId ORDER BY m.marketId DESC")
  Slice<Market> findHistoryByCursorId(Long cursorId, Pageable pageable);

  @Query("SELECT m FROM Market m ORDER BY m.marketId DESC")
  Slice<Market> findHistory(Pageable pageable);
}

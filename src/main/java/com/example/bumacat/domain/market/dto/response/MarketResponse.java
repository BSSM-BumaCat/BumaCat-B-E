package com.example.bumacat.domain.market.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MarketResponse (
        Long marketId,
        String title,
        String content,
        LocalDateTime scheduled_open_at
) {
}

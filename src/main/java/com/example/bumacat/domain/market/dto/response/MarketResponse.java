package com.example.bumacat.domain.market.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MarketResponse (
        Long marketId,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd/HH:mm")
        LocalDateTime scheduled_open_at
) {
}

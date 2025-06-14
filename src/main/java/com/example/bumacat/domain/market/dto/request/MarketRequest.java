package com.example.bumacat.domain.market.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record MarketRequest (
        String title,
        String content,

        @JsonFormat(pattern = "yyyy-MM-dd/HH:mm")
        LocalDateTime scheduled_open_at
) {
}

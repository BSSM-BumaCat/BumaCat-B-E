package com.example.bumacat.domain.product.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProductLikeRequest(
        @NotNull
        Long deviceId,
        @NotNull
        Long productId
) {
}

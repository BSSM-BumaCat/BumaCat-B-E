package com.example.bumacat.domain.product.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductUpdateRequest(
        @NotNull
        Long productId,
        @NotNull
        String title,
        @NotNull
        String description,
        @NotNull
        String thumbnailUrl,
        List<String> imageUrls
) {
}

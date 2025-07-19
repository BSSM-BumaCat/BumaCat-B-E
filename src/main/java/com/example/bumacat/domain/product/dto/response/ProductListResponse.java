package com.example.bumacat.domain.product.dto.response;

import com.example.bumacat.domain.product.model.Product;

import java.time.LocalDateTime;

public record ProductListResponse(
        Long productId,
        Long userId,
        String userName,
        String title,
        String thumbnailUrl,
        Long likeCount,
        LocalDateTime createdAt
) {
    public static ProductListResponse from(Product product) {
        return new ProductListResponse(
                product.getProductId(),
                product.getUser().getUserId(),
                product.getUser().getName(),
                product.getTitle(),
                product.getThumbnailUrl(),
                (long) product.getProductLikes().size(),
                product.getCreatedAt()
        );
    }
}

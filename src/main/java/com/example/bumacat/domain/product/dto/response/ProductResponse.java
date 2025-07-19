package com.example.bumacat.domain.product.dto.response;

import com.example.bumacat.domain.product.model.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ProductResponse(
        Long productId,
        Long userId,
        String userName,
        String title,
        String description,
        String thumbnailUrl,
        List<String> imageUrls,
        Long likeCount,
        LocalDateTime createdAt
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getUser().getUserId(),
                product.getUser().getName(),
                product.getTitle(),
                product.getDescription(),
                product.getThumbnailUrl(),
                product.getProductImages().stream()
                        .map(productImage -> productImage.getImageUrl())
                        .collect(Collectors.toList()),
                (long) product.getProductLikes().size(),
                product.getCreatedAt()
        );
    }
}

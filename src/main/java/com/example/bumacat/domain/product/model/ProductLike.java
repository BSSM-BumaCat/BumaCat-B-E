package com.example.bumacat.domain.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "`like`")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ProductLikeId.class)
public class ProductLike {
    @Id
    private Long deviceId;
    
    @Id
    @Column(name = "product_id")
    private Long productId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
    
    @CreatedDate
    private LocalDateTime likedAt;
    
    public static ProductLike of(Long deviceId, Product product) {
        return ProductLike.builder()
                .deviceId(deviceId)
                .productId(product.getProductId())
                .product(product)
                .build();
    }
}

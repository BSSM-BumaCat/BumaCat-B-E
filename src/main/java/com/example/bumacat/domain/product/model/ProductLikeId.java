package com.example.bumacat.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductLikeId implements Serializable {
    private Long deviceId;
    private Long productId;
}

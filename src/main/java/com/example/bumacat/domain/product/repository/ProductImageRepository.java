package com.example.bumacat.domain.product.repository;

import com.example.bumacat.domain.product.model.Product;
import com.example.bumacat.domain.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProduct(Product product);
    void deleteByProduct(Product product);
}

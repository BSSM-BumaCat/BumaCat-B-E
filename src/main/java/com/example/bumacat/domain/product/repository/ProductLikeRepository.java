package com.example.bumacat.domain.product.repository;

import com.example.bumacat.domain.product.model.Product;
import com.example.bumacat.domain.product.model.ProductLike;
import com.example.bumacat.domain.product.model.ProductLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeId> {
    
    @Query("SELECT COUNT(pl) FROM ProductLike pl WHERE pl.product = :product")
    Long countByProduct(@Param("product") Product product);
    
    Optional<ProductLike> findByDeviceIdAndProduct(Long deviceId, Product product);
    
    void deleteByDeviceIdAndProduct(Long deviceId, Product product);
}

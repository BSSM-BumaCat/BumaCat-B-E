package com.example.bumacat.domain.product.repository;

import com.example.bumacat.domain.product.model.Product;
import com.example.bumacat.domain.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.productLikes ORDER BY p.productId DESC")
    List<Product> findAllByOrderByProductIdDesc(Pageable pageable);
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.productLikes WHERE p.productId < :cursorId ORDER BY p.productId DESC")
    List<Product> findByProductIdLessThanOrderByProductIdDesc(@Param("cursorId") Long cursorId, Pageable pageable);
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.productLikes WHERE p.user = :user ORDER BY p.productId DESC")
    List<Product> findByUserOrderByProductIdDesc(@Param("user") User user, Pageable pageable);
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.productLikes WHERE p.user = :user AND p.productId < :cursorId ORDER BY p.productId DESC")
    List<Product> findByUserAndProductIdLessThanOrderByProductIdDesc(@Param("user") User user, @Param("cursorId") Long cursorId, Pageable pageable);
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.productLikes WHERE p.title LIKE %:search% OR p.description LIKE %:search% ORDER BY p.productId DESC")
    List<Product> findByTitleContainingOrDescriptionContainingOrderByProductIdDesc(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.productLikes WHERE (p.title LIKE %:search% OR p.description LIKE %:search%) AND p.productId < :cursorId ORDER BY p.productId DESC")
    List<Product> findByTitleContainingOrDescriptionContainingAndProductIdLessThanOrderByProductIdDesc(@Param("search") String search, @Param("cursorId") Long cursorId, Pageable pageable);
    
    @Query("SELECT p FROM Product p JOIN p.productLikes pl WHERE pl.deviceId = :deviceId ORDER BY p.productId DESC")
    List<Product> findLikedProductsByDeviceIdOrderByProductIdDesc(@Param("deviceId") Long deviceId);
}

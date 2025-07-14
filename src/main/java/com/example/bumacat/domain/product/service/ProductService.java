package com.example.bumacat.domain.product.service;

import com.example.bumacat.domain.product.dto.request.ProductLikeRequest;
import com.example.bumacat.domain.product.dto.request.ProductRequest;
import com.example.bumacat.domain.product.dto.request.ProductUpdateRequest;
import com.example.bumacat.domain.product.dto.response.ProductListResponse;
import com.example.bumacat.domain.product.dto.response.ProductResponse;
import com.example.bumacat.domain.product.exception.NotFoundProductException;
import com.example.bumacat.domain.product.exception.UnauthorizedProductAccessException;
import com.example.bumacat.domain.product.model.Product;
import com.example.bumacat.domain.product.model.ProductImage;
import com.example.bumacat.domain.product.model.ProductLike;
import com.example.bumacat.domain.product.model.ProductLikeId;
import com.example.bumacat.domain.product.repository.ProductImageRepository;
import com.example.bumacat.domain.product.repository.ProductLikeRepository;
import com.example.bumacat.domain.product.repository.ProductRepository;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.global.dto.CursorPage;
import com.example.bumacat.global.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductLikeRepository productLikeRepository;
    private final ImageUploadService imageUploadService;

    @Transactional
    public Long createProduct(User user, ProductRequest productRequest, List<MultipartFile> images) {
        // 첫 번째 이미지를 썸네일로 사용
        String thumbnailUrl = null;
        if (images != null && !images.isEmpty()) {
            thumbnailUrl = imageUploadService.uploadImage(images.get(0));
        }
        
        Product product = Product.of(user, productRequest.title(), productRequest.description(), thumbnailUrl);
        Product savedProduct = productRepository.save(product);
        
        // 나머지 이미지들을 ProductImage 테이블에 저장
        if (images != null && images.size() > 1) {
            List<ProductImage> productImages = images.stream()
                    .skip(1) // 첫 번째 이미지는 썸네일로 사용했으므로 건너뜀
                    .map(imageUploadService::uploadImage)
                    .map(imageUrl -> ProductImage.of(savedProduct, imageUrl))
                    .collect(Collectors.toList());
            productImageRepository.saveAll(productImages);
        }
        
        return savedProduct.getProductId();
    }

    public CursorPage<ProductListResponse> getMyProducts(User user, Long cursorId, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Product> products;
        
        if (cursorId == null) {
            products = productRepository.findByUserOrderByProductIdDesc(user, pageable);
        } else {
            products = productRepository.findByUserAndProductIdLessThanOrderByProductIdDesc(user, cursorId, pageable);
        }
        
        boolean hasNext = products.size() > size;
        if (hasNext) {
            products = products.subList(0, size);
        }
        
        List<ProductListResponse> productResponses = products.stream()
                .map(ProductListResponse::from)
                .collect(Collectors.toList());
        
        return new CursorPage<>(hasNext, productResponses);
    }

    public CursorPage<ProductListResponse> getProducts(Long cursorId, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Product> products;
        
        if (cursorId == null) {
            products = productRepository.findAllByOrderByProductIdDesc(pageable);
        } else {
            products = productRepository.findByProductIdLessThanOrderByProductIdDesc(cursorId, pageable);
        }
        
        boolean hasNext = products.size() > size;
        if (hasNext) {
            products = products.subList(0, size);
        }
        
        List<ProductListResponse> productResponses = products.stream()
                .map(ProductListResponse::from)
                .collect(Collectors.toList());
        
        return new CursorPage<>(hasNext, productResponses);
    }

    @Transactional
    public void likeProduct(ProductLikeRequest productLikeRequest) {
        Product product = productRepository.findById(productLikeRequest.productId())
                .orElseThrow(NotFoundProductException::getInstance);
        
        ProductLikeId productLikeId = new ProductLikeId(productLikeRequest.deviceId(), productLikeRequest.productId());
        
        // 이미 좋아요를 했는지 확인
        if (productLikeRepository.existsById(productLikeId)) {
            return; // 이미 좋아요를 한 경우 무시
        }
        
        ProductLike productLike = ProductLike.of(productLikeRequest.deviceId(), product);
        productLikeRepository.save(productLike);
    }

    public List<ProductListResponse> getLikedProducts(Long deviceId) {
        List<Product> products = productRepository.findLikedProductsByDeviceIdOrderByProductIdDesc(deviceId);
        
        return products.stream()
                .map(ProductListResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void unlikeProduct(ProductLikeRequest productLikeRequest) {
        ProductLikeId productLikeId = new ProductLikeId(productLikeRequest.deviceId(), productLikeRequest.productId());
        productLikeRepository.deleteById(productLikeId);
    }

    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundProductException::getInstance);
        
        return ProductResponse.from(product);
    }

    @Transactional
    public void updateProduct(User user, Long productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundProductException::getInstance);
        
        if (!product.getUser().getUserId().equals(user.getUserId())) {
            throw UnauthorizedProductAccessException.getInstance();
        }
        
        product.update(productUpdateRequest.title(), productUpdateRequest.description(), productUpdateRequest.thumbnailUrl());
        
        // 기존 이미지들 삭제
        productImageRepository.deleteByProduct(product);
        
        // 새 이미지들 저장
        if (productUpdateRequest.imageUrls() != null && !productUpdateRequest.imageUrls().isEmpty()) {
            List<ProductImage> productImages = productUpdateRequest.imageUrls().stream()
                    .map(imageUrl -> ProductImage.of(product, imageUrl))
                    .collect(Collectors.toList());
            productImageRepository.saveAll(productImages);
        }
    }

    @Transactional
    public void deleteProduct(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundProductException::getInstance);
        
        if (!product.getUser().getUserId().equals(user.getUserId())) {
            throw UnauthorizedProductAccessException.getInstance();
        }
        
        productRepository.delete(product);
    }

    public CursorPage<ProductListResponse> searchProducts(String search, Long cursorId, int size) {
        Pageable pageable = PageRequest.of(0, size + 1);
        List<Product> products;
        
        if (cursorId == null) {
            products = productRepository.findByTitleContainingOrDescriptionContainingOrderByProductIdDesc(search, pageable);
        } else {
            products = productRepository.findByTitleContainingOrDescriptionContainingAndProductIdLessThanOrderByProductIdDesc(search, cursorId, pageable);
        }
        
        boolean hasNext = products.size() > size;
        if (hasNext) {
            products = products.subList(0, size);
        }
        
        List<ProductListResponse> productResponses = products.stream()
                .map(ProductListResponse::from)
                .collect(Collectors.toList());
        
        return new CursorPage<>(hasNext, productResponses);
    }
}

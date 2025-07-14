package com.example.bumacat.domain.product.controller;

import com.example.bumacat.domain.product.dto.request.ProductLikeRequest;
import com.example.bumacat.domain.product.dto.request.ProductRequest;
import com.example.bumacat.domain.product.dto.request.ProductUpdateRequest;
import com.example.bumacat.domain.product.dto.response.ProductListResponse;
import com.example.bumacat.domain.product.dto.response.ProductResponse;
import com.example.bumacat.domain.product.service.ProductService;
import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.global.annotation.CurrentUser;
import com.example.bumacat.global.dto.CursorPage;
import com.example.bumacat.global.dto.ResponseDto;
import com.example.bumacat.global.util.HttpUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('SELLER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<Long>> createProduct(
            @CurrentUser User user, 
            @Valid @ModelAttribute ProductRequest productRequest,
            @RequestParam("images") List<MultipartFile> images) {
        Long productId = productService.createProduct(user, productRequest, images);
        ResponseDto<Long> responseDto = HttpUtil.success("product created", productId);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/my")
    public ResponseEntity<ResponseDto<CursorPage<ProductListResponse>>> getMyProducts(
            @CurrentUser User user,
            @RequestParam(value = "cursor-id", required = false) Long cursorId,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        CursorPage<ProductListResponse> products = productService.getMyProducts(user, cursorId, size);
        ResponseDto<CursorPage<ProductListResponse>> responseDto = HttpUtil.success("my products found", products);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<CursorPage<ProductListResponse>>> getProducts(
            @RequestParam(value = "cursor-id", required = false) Long cursorId,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        CursorPage<ProductListResponse> products = productService.getProducts(cursorId, size);
        ResponseDto<CursorPage<ProductListResponse>> responseDto = HttpUtil.success("products found", products);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/like")
    public ResponseEntity<ResponseDto<Void>> likeProduct(@Valid @RequestBody ProductLikeRequest productLikeRequest) {
        productService.likeProduct(productLikeRequest);
        ResponseDto<Void> responseDto = HttpUtil.success("product liked");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/like")
    public ResponseEntity<ResponseDto<List<ProductListResponse>>> getLikedProducts(
            @RequestParam("device-id") Long deviceId) {
        List<ProductListResponse> products = productService.getLikedProducts(deviceId);
        ResponseDto<List<ProductListResponse>> responseDto = HttpUtil.success("liked products found", products);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/like")
    public ResponseEntity<ResponseDto<Void>> unlikeProduct(@Valid @RequestBody ProductLikeRequest productLikeRequest) {
        productService.unlikeProduct(productLikeRequest);
        ResponseDto<Void> responseDto = HttpUtil.success("product unliked");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<ProductResponse>> getProduct(@PathVariable Long productId) {
        ProductResponse product = productService.getProduct(productId);
        ResponseDto<ProductResponse> responseDto = HttpUtil.success("product found", product);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @PatchMapping("/{productId}")
    public ResponseEntity<ResponseDto<Void>> updateProduct(
            @CurrentUser User user,
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        productService.updateProduct(user, productId, productUpdateRequest);
        ResponseDto<Void> responseDto = HttpUtil.success("product updated");
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto<Void>> deleteProduct(@CurrentUser User user, @PathVariable Long productId) {
        productService.deleteProduct(user, productId);
        ResponseDto<Void> responseDto = HttpUtil.success("product deleted");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<CursorPage<ProductListResponse>>> searchProducts(
            @RequestParam("search") String search,
            @RequestParam(value = "cursor-id", required = false) Long cursorId,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        CursorPage<ProductListResponse> products = productService.searchProducts(search, cursorId, size);
        ResponseDto<CursorPage<ProductListResponse>> responseDto = HttpUtil.success("products searched", products);
        return ResponseEntity.ok(responseDto);
    }
}

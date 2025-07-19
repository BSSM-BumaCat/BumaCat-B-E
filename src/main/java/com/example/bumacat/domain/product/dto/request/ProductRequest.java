package com.example.bumacat.domain.product.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductRequest(
        @NotNull
        String title,
        @NotNull
        String description
) {
}

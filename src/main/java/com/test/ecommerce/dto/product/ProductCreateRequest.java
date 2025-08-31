package com.test.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO de entrada para criação de produto (POST).
 * Aplica validações básicas com Bean Validation.
 */
public record ProductCreateRequest(
        @NotBlank String name,
        String description,
        @NotNull BigDecimal price,
        String category,
        @NotNull @Min(0) Integer stockQuantity
) {}

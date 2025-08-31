package com.test.ecommerce.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO de entrada para atualização de produto (PUT).
 * Mantemos as mesmas validações de criação: nome, preço e estoque obrigatórios.
 */
public record ProductUpdateRequest(
        @NotBlank String name,
        String description,
        @NotNull BigDecimal price,
        String category,
        @NotNull @Min(0) Integer stockQuantity
) {}

package com.test.ecommerce.dto.product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO de saída para produtos.
 * Usado nas respostas de listagem, busca por id, criação e atualização.
 */
public record ProductResponse (
    UUID id,
    String name,
    String description,
    BigDecimal price,
    String category,
    Integer stockQuantity,
    Instant createdAt,
    Instant updatedAt
) {}

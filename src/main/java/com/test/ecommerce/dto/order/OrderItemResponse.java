package com.test.ecommerce.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de saída para um item de pedido.
 * Usado nas respostas de /orders e /orders/my.
 */
public record OrderItemResponse(
        UUID id,
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {}

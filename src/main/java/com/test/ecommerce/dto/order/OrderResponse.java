package com.test.ecommerce.dto.order;

import com.test.ecommerce.entity.order.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO de saída para pedidos.
 * Usado em /orders, /orders/my e /orders/{id}/pay.
 */
public record OrderResponse(
        UUID id,
        OrderStatus status,
        BigDecimal totalValue,
        Instant createdAt,
        List<OrderItemResponse> items
) {}

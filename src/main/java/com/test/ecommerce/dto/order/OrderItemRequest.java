package com.test.ecommerce.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * DTO de entrada para um item do pedido.
 * Usado ao criar pedidos via POST /orders.
 */
public record OrderItemRequest(
        @NotNull UUID productId,
        @NotNull @Min(1) Integer quantity
) {}

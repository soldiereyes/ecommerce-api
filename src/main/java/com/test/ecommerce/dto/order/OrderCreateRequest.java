package com.test.ecommerce.dto.order;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * DTO de entrada para criação de um pedido.
 * Contém uma lista de itens obrigatória.
 */
public record OrderCreateRequest(
        @NotEmpty List<OrderItemRequest> items
) {}

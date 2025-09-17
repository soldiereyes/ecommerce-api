package com.test.ecommerce.service.order;

import com.test.ecommerce.dto.order.*;
import com.test.ecommerce.entity.order.Order;
import com.test.ecommerce.entity.order.OrderItem;
import com.test.ecommerce.entity.order.OrderStatus;
import com.test.ecommerce.entity.product.Product;
import com.test.ecommerce.entity.user.User;
import com.test.ecommerce.repository.order.OrderRepository;
import com.test.ecommerce.repository.product.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    /**
     * Cria um pedido em status PENDING com itens fornecidos.
     */
    @Transactional
    public OrderResponse createPending(User user, OrderCreateRequest req) {
        if (req.items() == null || req.items().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .build();

        // Carregar todos os produtos de uma vez
        Set<UUID> ids = new HashSet<>();
        for (var it : req.items()) ids.add(it.productId());
        Map<UUID, Product> map = new HashMap<>();
        productRepository.findAllById(ids).forEach(p -> map.put(p.getId(), p));

        // Criar os itens do pedido
        for (var it : req.items()) {
            Product p = map.get(it.productId());
            if (p == null) {
                throw new IllegalArgumentException("Product not found: " + it.productId());
            }

            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .product(p)
                    .quantity(it.quantity())
                    .price(p.getPrice()) // snapshot do preço
                    .build();
            order.addItem(oi);
        }

        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    /**
     * Pagar um pedido:
     * - Só o dono pode pagar
     * - Se não houver estoque, cancela
     * - Se houver, debita estoque e confirma
     */
    @Transactional
    public OrderResponse pay(User user, UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You are not allowed to pay this order");
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            return toResponse(order); // idempotência
        }

        // Verificar estoque
        for (OrderItem oi : order.getItems()) {
            if (oi.getProduct().getStockQuantity() < oi.getQuantity()) {
                order.setStatus(OrderStatus.CANCELED);
                return toResponse(order);
            }
        }

        // Debitar estoque e calcular total
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem oi : order.getItems()) {
            Product p = oi.getProduct();
            p.setStockQuantity(p.getStockQuantity() - oi.getQuantity());
            total = total.add(oi.getSubtotal());
        }
        order.setTotalValue(total);
        order.setStatus(OrderStatus.PAID);

        return toResponse(order);
    }

    /**
     * Lista os pedidos do usuário autenticado.
     */
    @Transactional
    public Page<OrderResponse> listMy(User user, Pageable pageable) {
        return orderRepository.findByUserId(user.getId(), pageable)
                .map(this::toResponse);
    }

    /* ===== mapeamento para DTO ===== */
    private OrderResponse toResponse(Order o) {
        var items = o.getItems().stream().map(oi ->
                new OrderItemResponse(
                        oi.getId(),
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getPrice()
                )
        ).toList();

        return new OrderResponse(
                o.getId(),
                o.getStatus(),
                o.getTotalValue(),
                o.getCreatedAt(),
                items
        );
    }
}

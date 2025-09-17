package com.test.ecommerce.controller.order;

import com.test.ecommerce.dto.order.OrderCreateRequest;
import com.test.ecommerce.dto.order.OrderResponse;
import com.test.ecommerce.entity.user.User;
import com.test.ecommerce.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.UUID;

/**
 * Endpoints REST de Pedidos.
 * - POST /orders → cria pedido PENDING
 * - POST /orders/{id}/pay → paga um pedido
 * - GET /orders/my → lista pedidos do usuário autenticado
 */
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    /** Cria um pedido com status PENDING */
    @PostMapping
    public ResponseEntity<OrderResponse> create(@AuthenticationPrincipal User user,
                                                @Valid @RequestBody OrderCreateRequest body) {
        return ResponseEntity.ok(service.createPending(user, body));
    }

    /** Paga um pedido (se estoque disponível, vira PAID; senão, CANCELED) */
    @PostMapping("/{id}/pay")
    public ResponseEntity<OrderResponse> pay(@AuthenticationPrincipal User user,
                                             @PathVariable UUID id) {
        return ResponseEntity.ok(service.pay(user, id));
    }

    /** Lista os pedidos do usuário autenticado */
    @GetMapping("/my")
    public ResponseEntity<Page<OrderResponse>> my(@AuthenticationPrincipal User user,
                                                  @PageableDefault(size = 20) Pageable pageable) { // verificar se há uma opção para quando não há registros suficientes para paginar.
        return ResponseEntity.ok(service.listMy(user, pageable));
    }
}

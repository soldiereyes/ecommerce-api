package com.test.ecommerce.repository.order;

import com.test.ecommerce.entity.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> { }

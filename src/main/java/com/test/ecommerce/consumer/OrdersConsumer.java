package com.test.ecommerce.consumer;

import com.test.ecommerce.dto.order.OrderCreateRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrdersConsumer {

    @KafkaListener(topics = "pedido-criado", groupId = "orders-group")
    public void consume(@Payload OrderCreateRequest payload) {
        System.out.println("Consuming OrderCreateRequest: " + payload);
    }
}

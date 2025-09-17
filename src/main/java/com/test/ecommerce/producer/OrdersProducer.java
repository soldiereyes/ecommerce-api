package com.test.ecommerce.producer;

import com.test.ecommerce.dto.order.OrderResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrdersProducer {
    private final KafkaTemplate<String, OrderResponse> kafkaTemplate;

    public OrdersProducer(KafkaTemplate<String, OrderResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(OrderResponse payload) {
        kafkaTemplate.send("predido-criado", payload);
    }
}

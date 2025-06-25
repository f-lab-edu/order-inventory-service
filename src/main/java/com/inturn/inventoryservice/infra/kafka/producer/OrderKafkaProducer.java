package com.inturn.inventoryservice.infra.kafka.producer;

import com.inturn.inventoryservice.domain.order.dto.event.CreateOrderEvent;
import com.inturn.inventoryservice.infra.kafka.define.OrderConst;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderKafkaProducer {

    private final KafkaTemplate<String, CreateOrderEvent> kafkaTemplate;

    public void sendOrderInfo(CreateOrderEvent record) {
        kafkaTemplate.send(OrderConst.ORDER_TOPIC, record);
    }
}

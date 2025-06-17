package com.inturn.inventoryservice.infra.kafka.producer;

import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import com.inturn.inventoryservice.infra.kafka.define.OrderConst;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, CreateOrderRecord> kafkaTemplate;

    public void sendOrder(CreateOrderRecord record) {
        kafkaTemplate.send(OrderConst.ORDER_TOPIC, record);
    }
}

package com.inturn.inventoryservice.infra.kafka.producer;

import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class OrderProducer {

	private final KafkaTemplate<String, CreateOrderRecord> kafkaTemplate;

	private final String ORDER_TOPIC = "order-topic";

	public void sendOrder(CreateOrderRecord record) {
		kafkaTemplate.send(ORDER_TOPIC, record);
	}
}

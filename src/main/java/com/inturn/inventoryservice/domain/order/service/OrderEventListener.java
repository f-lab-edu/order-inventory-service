package com.inturn.inventoryservice.domain.order.service;

import com.inturn.inventoryservice.domain.order.define.OrderStatus;
import com.inturn.inventoryservice.domain.order.dto.event.CompleteOrderEvent;
import com.inturn.inventoryservice.domain.order.dto.event.CreateOrderEvent;
import com.inturn.inventoryservice.infra.kafka.producer.OrderKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final OrderKafkaProducer orderKafkaProducer;

    private final OrderCommandService orderCommandService;

    @TransactionalEventListener
    public void sendOrderProducer(CreateOrderEvent evt) {
        orderKafkaProducer.sendOrderInfo(evt);
    }

    @TransactionalEventListener
    public void completeOrderEvent(CompleteOrderEvent evt) {
        orderCommandService.updateStatus(evt.orderId(), OrderStatus.COMPLETE);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void cancelOrderEvent(CompleteOrderEvent evt) {
        orderCommandService.updateStatus(evt.orderId(), OrderStatus.CANCEL);
    }

}

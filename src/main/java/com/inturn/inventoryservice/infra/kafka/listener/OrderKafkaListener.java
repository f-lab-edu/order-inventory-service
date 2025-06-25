package com.inturn.inventoryservice.infra.kafka.listener;

import com.inturn.inventoryservice.domain.inventory.facade.InventoryDeductFacade;
import com.inturn.inventoryservice.domain.order.dto.event.CreateOrderEvent;
import com.inturn.inventoryservice.infra.kafka.define.OrderConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaListener {

    private final InventoryDeductFacade inventoryDeductFacade;

    @KafkaListener(topics = OrderConst.ORDER_TOPIC)
    public void createOrderListener(CreateOrderEvent evt) {

        //주문 등록 & 재고 감소 처리
        inventoryDeductFacade.deductInventoryWithCompleteOrder(evt);
    }
}

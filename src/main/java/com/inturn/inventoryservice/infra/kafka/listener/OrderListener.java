package com.inturn.inventoryservice.infra.kafka.listener;

import com.inturn.inventoryservice.domain.inventory.facade.InventoryDeductFacade;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import com.inturn.inventoryservice.domain.order.facade.OrderCreateFacade;
import com.inturn.inventoryservice.infra.kafka.define.OrderConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderCreateFacade orderCreateFacade;

    private final InventoryDeductFacade inventoryDeductFacade;

    @KafkaListener(topics = OrderConst.ORDER_TOPIC)
    public void createOrderListener(CreateOrderRecord order) {

        //주문 등록 & 재고 감소 처리
        orderCreateFacade.createOrderAndDeductInventory(order);
    }
}

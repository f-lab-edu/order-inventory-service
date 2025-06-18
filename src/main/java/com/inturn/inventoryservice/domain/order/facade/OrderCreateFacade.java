package com.inturn.inventoryservice.domain.order.facade;

import com.inturn.inventoryservice.domain.inventory.facade.InventoryDeductFacade;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderItemRecord;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import com.inturn.inventoryservice.domain.order.entity.OrderEntity;
import com.inturn.inventoryservice.domain.order.service.OrderCommandService;
import com.inturn.inventoryservice.domain.orderdetail.entity.OrderDetailEntity;
import com.inturn.inventoryservice.domain.orderdetail.service.OrderDetailCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCreateFacade {

    private final OrderCommandService orderCommandService;

    private final OrderDetailCommandService orderDetailCommandService;

    private final InventoryDeductFacade inventoryDeductFacade;

    @Transactional
    public void createOrderAndDeductInventory(CreateOrderRecord order) {

        //주문 저장
        OrderEntity orderEntity = orderCommandService.save(OrderEntity.from(order));

        validateExistItem(order.itemList());

        //주문 상세 저장
        order.itemList().forEach(o -> orderDetailCommandService.save(OrderDetailEntity.from(o, orderEntity.getOrderId())));

        inventoryDeductFacade.deductInventory(order);
    }

    private void validateExistItem(List<CreateOrderItemRecord> itemList) {

        //TODO - 아이템의 존재유무 확인 validate
    }
}

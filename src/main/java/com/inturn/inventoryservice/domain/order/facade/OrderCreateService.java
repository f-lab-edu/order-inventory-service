package com.inturn.inventoryservice.domain.order.facade;

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
public class OrderCreateService {

    private final OrderCommandService orderCommandService;

    private final OrderDetailCommandService orderDetailCommandService;

    @Transactional
    public void createOrder(CreateOrderRecord order) {

        //주문 저장
        orderCommandService.save(OrderEntity.from(order));

        validateExistItem(order.itemList());

        //주문 상세 저장
        order.itemList().forEach(o -> orderDetailCommandService.save(OrderDetailEntity.from(o)));
    }

    private void validateExistItem(List<CreateOrderItemRecord> itemList) {

        //TODO - 아이템의 존재유무 확인 validate
    }
}

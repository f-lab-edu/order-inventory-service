package com.inturn.inventoryservice.domain.order.service;

import com.inturn.inventoryservice.domain.order.define.OrderStatus;
import com.inturn.inventoryservice.domain.order.define.error.OrderErrorCode;
import com.inturn.inventoryservice.domain.order.entity.OrderEntity;
import com.inturn.inventoryservice.domain.order.exception.OrderException;
import com.inturn.inventoryservice.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderEntity save(OrderEntity entity) {
        return orderRepository.save(entity);
    }

    @Transactional
    public void updateStatus(Long orderId, OrderStatus orderStatus) {
        Optional<OrderEntity> optEntity = orderRepository.findById(orderId);

        if(optEntity.isEmpty()) {
            throw new OrderException(OrderErrorCode.ORDER_NOT_FOUND_EXCEPTION);
        }

        OrderEntity entity = optEntity.get();
        entity.updateOrderStatus(orderStatus);
        this.save(entity);
    }
}

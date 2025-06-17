package com.inturn.inventoryservice.domain.order.service;

import com.inturn.inventoryservice.domain.order.entity.OrderEntity;
import com.inturn.inventoryservice.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderEntity save(OrderEntity entity) {
        return orderRepository.save(entity);
    }

}

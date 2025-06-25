package com.inturn.inventoryservice.domain.orderdetail.service;

import com.inturn.inventoryservice.domain.orderdetail.entity.OrderDetailEntity;
import com.inturn.inventoryservice.domain.orderdetail.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailCommandService {

    private final OrderDetailRepository orderDetailRepository;

    @Transactional
    public void save(OrderDetailEntity entity) {
        orderDetailRepository.save(entity);
    }
}

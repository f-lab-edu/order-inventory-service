package com.inturn.inventoryservice.domain.orderdetail.repository;

import com.inturn.inventoryservice.domain.orderdetail.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {

}

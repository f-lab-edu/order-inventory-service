package com.inturn.inventoryservice.domain.order.repository;

import com.inturn.inventoryservice.domain.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}

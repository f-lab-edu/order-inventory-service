package com.inturn.inventoryservice.domain.order.entity;

import com.inturn.inventoryservice.domain.order.define.OrderStatus;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "order")
@Getter
@SuperBuilder
@NoArgsConstructor
public class OrderEntity extends Order{

    public static OrderEntity from(CreateOrderRecord record) {
        return OrderEntity.builder()
                .userId(record.userId())
                .address(record.address())
                .orderStatusCd(OrderStatus.READY)
                .build();
    }
}

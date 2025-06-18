package com.inturn.inventoryservice.domain.orderdetail.entity;

import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderItemRecord;
import com.inturn.inventoryservice.domain.order.entity.OrderEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "order_detail")
@Getter
@SuperBuilder
@NoArgsConstructor
public class OrderDetailEntity extends OrderDetail{

    @ManyToOne
    @JoinColumn(updatable = false, insertable = false, name = "orderId")
    private OrderEntity order;

    public static OrderDetailEntity from(CreateOrderItemRecord record, Long orderId) {
        return OrderDetailEntity.builder()
                .orderId(orderId)
                .itemId(record.itemId())
                .itemName(record.itemName())
                .price(record.price())
                .orderQty(record.orderQty())
                .build();

    }
}

package com.inturn.inventoryservice.domain.order.entity;

import com.inturn.inventoryservice.domain.order.define.OrderStatus;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "orders")
@Getter
@SuperBuilder
@NoArgsConstructor
public class OrderEntity {

    //TODO - 공통 관련 entity를 구성하여 상속받아 처리.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatusCode;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String address;

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatusCode = orderStatus;
    }

    public static OrderEntity of(CreateOrderRecord record) {
        return OrderEntity.builder()
                .userId(record.userId())
                .address(record.address())
                .orderStatusCode(OrderStatus.READY)
                .build();
    }
}

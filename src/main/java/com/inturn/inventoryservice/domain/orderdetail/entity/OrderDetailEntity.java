package com.inturn.inventoryservice.domain.orderdetail.entity;

import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderItemRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "order_detail")
@Getter
@SuperBuilder
@NoArgsConstructor
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String itemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer orderQty;

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

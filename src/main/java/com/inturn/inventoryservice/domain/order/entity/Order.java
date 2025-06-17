package com.inturn.inventoryservice.domain.order.entity;

import com.inturn.inventoryservice.domain.order.define.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    //TODO - 공통 관련 entity를 구성하여 상속받아 처리.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    private Long orderId;

    @Column(nullable = false)
    private OrderStatus orderStatusCd;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String address;

}

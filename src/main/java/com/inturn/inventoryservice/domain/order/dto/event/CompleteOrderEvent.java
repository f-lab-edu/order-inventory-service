package com.inturn.inventoryservice.domain.order.dto.event;

public record CompleteOrderEvent(
        Long orderId
) {
}

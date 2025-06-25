package com.inturn.inventoryservice.domain.order.dto.event;

import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderItemRecord;

import java.util.List;

public record CreateOrderEvent (

        Long orderId,

        String userId,

        String address,

        List<CreateOrderItemRecord> itemList
)
{
}

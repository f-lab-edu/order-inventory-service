package com.inturn.inventoryservice.domain.order.dto.request;

import lombok.Builder;

@Builder
public record CreateOrderItemRecord(

		String itemId,

		Integer orderQty
) {
}

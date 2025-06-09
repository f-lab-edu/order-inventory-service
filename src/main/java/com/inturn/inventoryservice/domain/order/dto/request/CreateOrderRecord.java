package com.inturn.inventoryservice.domain.order.dto.request;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Builder
public record CreateOrderRecord(

		String orderId,

		String userId,

		String address,


		List<CreateOrderItemRecord> itemList
) {
}

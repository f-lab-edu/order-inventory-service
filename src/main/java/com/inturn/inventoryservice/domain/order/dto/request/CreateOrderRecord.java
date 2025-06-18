package com.inturn.inventoryservice.domain.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record CreateOrderRecord(

		@NotEmpty
		String userId,

		@NotEmpty
		String address,

		@NotNull
		List<CreateOrderItemRecord> itemList
) {
}

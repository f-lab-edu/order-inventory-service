package com.inturn.inventoryservice.domain.inventory.define;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InventoryErrorCode {

	ITEM_NOT_FOUND_EXCEPTION("해당 제품 재고는 존재하지 않습니다."),

	INVENTORY_OUT_OF_STOCK("해당 제품의 재고는 부족한 상태입니다.")
	;

	private String errorMessage;
}

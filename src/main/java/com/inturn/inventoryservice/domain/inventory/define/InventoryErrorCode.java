package com.inturn.inventoryservice.domain.inventory.define;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InventoryErrorCode {

	ITEM_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 제품 재고는 존재하지 않습니다."),

	INVENTORY_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "제품명 - %s 의 재고가 부족합니다.")
	;

	final private HttpStatus httpStatus;
	final private String errorMessage;
}

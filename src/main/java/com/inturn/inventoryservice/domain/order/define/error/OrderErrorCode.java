package com.inturn.inventoryservice.domain.order.define.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {

	ORDER_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 주문은 존재하지 않습니다."),


	;

	final private HttpStatus httpStatus;
	final private String errorMessage;
}

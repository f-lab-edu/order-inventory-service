package com.inturn.inventoryservice.domain.order.exception;


import com.inturn.inventoryservice.domain.order.define.error.OrderErrorCode;
import com.inturn.inventoryservice.global.common.exception.BaseException;

public class OrderException extends BaseException {

	public OrderException(OrderErrorCode orderErrorCode) {
		super(orderErrorCode.getHttpStatus(), orderErrorCode.getErrorMessage());
	}
}

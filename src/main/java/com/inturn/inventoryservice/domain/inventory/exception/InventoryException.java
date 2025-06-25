package com.inturn.inventoryservice.domain.inventory.exception;


import com.inturn.inventoryservice.domain.inventory.define.InventoryErrorCode;
import com.inturn.inventoryservice.global.common.exception.BaseException;

public class InventoryException extends BaseException {

	public InventoryException(InventoryErrorCode inventoryErrorCode) {
		super(inventoryErrorCode.getHttpStatus(), inventoryErrorCode.getErrorMessage());
	}

	public InventoryException(InventoryErrorCode inventoryErrorCode, String errorMessage) {
		super(inventoryErrorCode.getHttpStatus(), errorMessage);
	}
}

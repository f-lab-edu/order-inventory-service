package com.inturn.inventoryservice.global.common.exception;


import com.inturn.inventoryservice.global.common.dto.ErrorCodeDTO;
import lombok.Getter;

public class InventoryServiceException extends RuntimeException{

	@Getter
	private Integer status;
	protected InventoryServiceException(ErrorCodeDTO error) {
		super(error.getDefaultErrorMessage());
		this.status = error.getStatusValue();
	}
}

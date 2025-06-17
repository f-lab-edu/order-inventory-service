package com.inturn.inventoryservice.global.common.exception;


import com.inturn.inventoryservice.global.common.dto.ErrorCodeDTO;
import com.inturn.inventoryservice.global.common.exception.vo.CommonErrorCode;

public class NotFoundException extends InventoryServiceException {

	public NotFoundException() {
		super(CommonErrorCode.NOT_FOUND_EXCEPTION.getError());
	}

	public NotFoundException(ErrorCodeDTO dto) {
		super(dto);
	}
	//TODO - 변수를 넘겨받아 Message를 가공하는 부분도 확인

}

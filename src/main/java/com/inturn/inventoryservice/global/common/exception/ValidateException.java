package com.inturn.inventoryservice.global.common.exception;

import com.inturn.inventoryservice.global.common.dto.ErrorCodeDTO;

public class ValidateException extends InventoryServiceException {

    //TODO - 해당 로직은 임시로 아래와 같이 처리 추후 변경.
    public ValidateException(String message) {
        super(ErrorCodeDTO.builder()
                .defaultErrorMessage(message)
                .build());
    }

    public ValidateException(ErrorCodeDTO error) {
        super(error);
    }
}

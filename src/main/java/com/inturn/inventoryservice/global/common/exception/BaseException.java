package com.inturn.inventoryservice.global.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{

	@Getter
	private final HttpStatus httpStatus;
	protected BaseException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.httpStatus = httpStatus;
	}
}

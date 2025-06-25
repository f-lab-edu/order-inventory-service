package com.inturn.inventoryservice.global.common.exception.vo;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode {

	NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "해당 데이터는 존재하지 않습니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "API 호출 권한이 없습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 정보가 존재하지 않습니다."),
	HTTP_MESSAGE_NOT_READABLE_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 데이터가 올바르지 않습니다."),
	NOT_FOUND_SESSION_EXCEPTION(HttpStatus.NOT_FOUND, "Session 정보가 존재하지 않습니다."),
	;

	private final HttpStatus httpStatus;
	private final String errorMessage;

	CommonErrorCode(HttpStatus httpStatus, String errorMessage) {
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
}

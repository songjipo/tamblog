package com.tjck.tamblog.utils;

public enum ResultEnum {
	UNKNOWN_ERROR(-1,"未知错误"),
	SUCCESS(0,"成功"),
	//ERROR()
	;
	private Integer code;
	private String message;
	
	ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}

package com.tjck.tamblog.exception;

import com.tjck.tamblog.utils.ResultEnum;

public class MyCustomException extends RuntimeException{
	
	private static final long serialVersionUID = 2037814774136358247L;
	
	private Integer code;
	
	public MyCustomException(ResultEnum resultEnum){
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
	}
	
	public MyCustomException(){
		
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}

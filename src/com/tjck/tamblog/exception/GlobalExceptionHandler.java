package com.tjck.tamblog.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.tjck.tamblog.utils.ResultInfo;
import com.tjck.tamblog.utils.ResultUtil;

@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler {
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResultInfo CustomException(HttpServletRequest request, Exception e){
		/*ResultInfo resultInfo = new ResultInfo();
		resultInfo.setCode(ResultInfo.ERROR);
		resultInfo.setMessage(e.getMessage());
		resultInfo.setUrl(request.getRequestURI().trim().toString());*/
		if (e instanceof MyCustomException) {
			MyCustomException myCustomException = (MyCustomException) e;
			return ResultUtil.error(myCustomException.getCode(), myCustomException.getMessage());
		}else{
			return ResultUtil.error(-1, "未知错误");
		}
	}
	
}

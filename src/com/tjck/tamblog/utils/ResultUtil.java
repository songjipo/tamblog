package com.tjck.tamblog.utils;

public class ResultUtil {
	
	public static ResultInfo success(Object object){
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setCode(ResultInfo.OK);
		resultInfo.setMessage("操作成功");
		resultInfo.setData(object);
		return resultInfo;
	}
	
	public static ResultInfo success(){
		return success(null);
	}
	
	public static ResultInfo error(Integer code, String message){
		ResultInfo resultInfo = new ResultInfo();
		resultInfo.setCode(code);
		resultInfo.setMessage(message);
		return resultInfo;
	}
	
}

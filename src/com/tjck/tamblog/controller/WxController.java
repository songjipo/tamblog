package com.tjck.tamblog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.entity.Twxtoken;
import com.tjck.tamblog.service.IWxService;
import com.tjck.tamblog.utils.HttpsRequest;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wx")
public class WxController extends BaseController{
	
	public static final String QR_SCENE = "QR_SCENE";  //临时整型
	public static final String QR_STR_SCENE = "QR_STR_SCENE";  //临时字符串
	public static final String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";  //永久整型
	public static final String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE"; //永久字符串
	
	@Autowired
	private IWxService wxService;
	
	@RequestMapping("/toajax")
	private String toAjax() {
		return "ajax/ajax";
	}
	
	@RequestMapping("/ajax")
	private String testAjax(String username, String password) {
		if ("1".equals(username) && "1".equals(password)) {
			return "ajax/ajax";
		}else
			return "ajax/error";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxback")
	private String backajax(String name, String age) {
		System.err.println(name);
		System.err.println(age);
		Twxtoken token = new Twxtoken();
		token.setAppid("appid");
		token.setSeconds("600");
		JSONObject object = JSONObject.fromObject(token);
		return object.toString();
	}
	
	@ResponseBody
	@RequestMapping("/getAT")
	private String getAccessToken() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String wxtoken = "";
		String hql = "from Twxtoken t";
		Twxtoken token = wxService.getbyhql(hql);
		Date curdate = Calendar.getInstance().getTime();
		if (token.getTime() != null && token.getTime().length() > 0) {
			String tokentime = token.getTime();
			String seconds = token.getSeconds();
			Date d = sdf.parse(tokentime);
			int expire = (int)(((curdate.getTime() - d.getTime())/1000));
			if (expire > Integer.valueOf(seconds)) {
				String url = "https://api.weixin.qq.com/cgi-bin/token";
				String result = HttpsRequest.sendGet(url, "grant_type=client_credential&appid=wx20f86934e826f9dd&secret=4d53cc523061b5a6360d97ae813608a4");
				JSONObject jsonObject = JSONObject.fromObject(result);
				wxtoken = jsonObject.getString("access_token");
				token.setAccesstoken(wxtoken);
				token.setTime(sdf.format(curdate));
				wxService.edit(token);
			}else {
				wxtoken = token.getAccesstoken();
			}
		}else {
			String url = "https://api.weixin.qq.com/cgi-bin/token";
			String result = HttpsRequest.sendGet(url, "grant_type=client_credential&appid=wx20f86934e826f9dd&secret=4d53cc523061b5a6360d97ae813608a4");
			JSONObject jsonObject = JSONObject.fromObject(result);
			wxtoken = jsonObject.getString("access_token");
			token.setAccesstoken(wxtoken);
			token.setTime(sdf.format(curdate));
			wxService.edit(token);
		}
		return wxtoken;
	}
	
	@ResponseBody
	@RequestMapping("/getTicket")
	private String getQRTicket(String type, String content, String seconds)
			throws ParseException {
		String param = "";
		switch (type) {
		case "1":
			param = "{\"expire_seconds\": " +  seconds +", \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+ content +"}}}";
			break;
		case "2":
			param = "{\"expire_seconds\": " + seconds +", \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + content + "\"}}}";
			break;
		case "3":
			param = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + content + "}}}";
			break;
		case "4":
			param = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+ content +"\"}}}";
			break;
		}
		String accesstoken = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accesstoken;
		String result = HttpsRequest.sendPostJson(url, param);
		JSONObject jsonObject = JSONObject.fromObject(result);
		String ticket = jsonObject.getString("ticket");
		return ticket;
	}
	
	@RequestMapping("/getCode")
	private String getQRCode(@RequestParam(value = "type") String type,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "seconds") String seconds, HttpServletRequest request)
			throws ParseException {
		String stype = request.getParameter("type");
		String scontent = request.getParameter("content");
		String sseconds = request.getParameter("seconds");
		String ticket = getQRTicket(stype, scontent, sseconds);
		
		String path = request.getServletContext().getRealPath("/upload") + "/code.jpg";
		System.err.println(ticket);
		System.err.println(path);
		try {
			String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "UTF-8");
			URL apiurl = new URL(url);
			HttpURLConnection conn = HttpsRequest.getConnection("GET", apiurl);
			//String contentType = conn.getContentType();
			InputStream input = conn.getInputStream();
			File file = new File(path);
			FileOutputStream output = new FileOutputStream(file);
			int len = 0;
			byte[] array = new byte[1024];
			while ((len = input.read(array)) != -1) {
				output.write(array, 0, len);
			}
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("ticket", ticket);
		return "wx/qrcode";
	}
	
}

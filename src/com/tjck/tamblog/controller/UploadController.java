package com.tjck.tamblog.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tjck.tamblog.controller.base.BaseController;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(UploadController.class);
	
	@RequestMapping("/tofile")
	public String filejsp() {
		return "/info/infoadd";
	}
	
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request, MultipartFile picfile) throws Exception {
		String picRealPathDir = request.getSession().getServletContext().getRealPath("/upload");
		String ext = FilenameUtils.getExtension(picfile.getOriginalFilename());
		String picName = picfile.getOriginalFilename();
		String picname = picName.substring(0, picName.indexOf("."));
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = dateformat.format(Calendar.getInstance().getTime());
		File targetFile = new File(picRealPathDir + File.separator + picname + time + "." + ext);
		if(!targetFile.exists()){
            targetFile.mkdirs();
        }
		picfile.transferTo(targetFile);
		return "info/success";
	}
	
}

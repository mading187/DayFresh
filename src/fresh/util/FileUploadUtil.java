package fresh.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;


import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
 

public class FileUploadUtil {
	public static String PATH = "../pics"; // 上传路径
	private static final String ALLOWEDLIST = "gif,jpg,png,jpeg,doc,docx,xls,xlsx,txt "; // 允许文件的后缀
	private static final int MAXFILESIZE = 10 * 1024 * 1024;// 单个文件的总大小
	private static final int TOTALMAXSIZE = 100 * 1024 * 1024; // 每次上传文件的总大小s
	private String basePath;

	public Map<String, String> upload(PageContext pageContext) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		// 实例化上传组件
		SmartUpload su = new SmartUpload();
		su.initialize(pageContext); // 初始化上传组件

		// 设置参数
		su.setMaxFileSize(MAXFILESIZE);
		su.setTotalMaxFileSize(TOTALMAXSIZE);
		su.setAllowedFilesList(ALLOWEDLIST);
		su.setCharset("utf-8");
		su.upload(); // 开始上传

		// 获取非文件参数
		Request req = su.getRequest();
		Enumeration<String> enums = req.getParameterNames();

		String name = null;
		while (enums.hasMoreElements()) {
			name = enums.nextElement();
			map.put(name, req.getParameter(name));
		}

		// 处理上传的文件
		Files files = su.getFiles();
		if (files == null || files.getCount() <= 0) {
			return map;
		}

		Collection<File> fls = files.getCollection();
		// 获取保存文件夹的绝对路径 ->webapps
		basePath = pageContext.getRequest().getRealPath("/");

		String fieldName = null;
		String fileName = null;
		String temp = null;
		String pathStr = "";
		for (File fl : fls) {
			System.out.println(fl.getFieldName());
			if (!fl.isMissing()) { // 说明是第一个要上传的文件
				temp = fl.getFieldName(); // photo myFile
				if (Stringutil.checkNull(fieldName)) {
					fieldName = temp;
				} else { // 说明时第二个文本框的内容
					if (!temp.equals(fieldName)) {
						System.out.println(pathStr);
						// 首先将上一个文本框的内容存到map中
						map.put(fieldName, pathStr);
						pathStr = "";// 初始化一下，准备存放下一个文件的路径
						fieldName = temp;
					}
				}

				// 存到服务器中 ->获取tomcat在服务器中的绝对路径
//		fieldName = fl.getFieldName();
				fileName = PATH + "/" + new Date().getTime() + "_" + fl.getFileName();

				if (Stringutil.checkNull(pathStr)) {
					pathStr = fileName;

				} else {
					pathStr += "," + fileName;
				}
				// 保存到服务区
				fl.saveAs(basePath + fileName, SmartUpload.SAVE_PHYSICAL);
			}
		}
		map.put(fieldName, pathStr);
		return map;
	}
	
	/**
	 * 只针对单文件文本框的但文件上传
	 * @param <T>
	 * @param clazz
	 * @param pageContext
	 * @return
	 * @throws Exception
	 */
	public Map<String , String> uploadPic(PageContext pageContext) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();

		
		//实例化上传组件
		SmartUpload su = new SmartUpload();
		su.initialize(pageContext);  // 初始化上传组件组件
		
		
		// 设置参数
				su.setMaxFileSize(MAXFILESIZE);
				su.setTotalMaxFileSize(TOTALMAXSIZE);
				su.setAllowedFilesList(ALLOWEDLIST);
				su.setCharset("utf-8");
				su.upload(); // 开始上传

				
				// 处理上传的文件
				Files files = su.getFiles();
				if (files == null || files.getCount() <= 0) {
					return map;
				}

				Collection<File> fls = files.getCollection();
				// 获取保存文件夹的绝对路径 ->webapps
				basePath = pageContext.getRequest().getRealPath("/");

				String fieldName = null;
				String fileName = null;
				String uploadPath = null;
				for (File fl : fls) {
//					System.out.println(fl.getFieldName());
					if (!fl.isMissing()) { // 说明是第一个要上传的文件
						fieldName = fl.getFieldName();
						fileName = fl.getFileName();
						}

						// 存到服务器中 ->获取tomcat在服务器中的绝对路径
//				fieldName = fl.getFieldName();
						uploadPath = PATH + "/" + new Date().getTime() + "_" + fl.getFileName();

						// 保存到服务区
						fl.saveAs(basePath + uploadPath, SmartUpload.SAVE_PHYSICAL);
						
					}
				
				map.put(fieldName, uploadPath);
				map.put("fileName", fileName);
				return map;
	}
}

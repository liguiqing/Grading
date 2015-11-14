package com.easytnt.commons.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.util.Closer;


/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年11月10日
 * @version 1.0
 **/
public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static File inputStreamToFile(HttpServletRequest request,String imagedir,InputStream in,String fileName) throws Exception{
		String root = request.getServletContext().getRealPath("/");
		File file = new File(root+ File.separator+imagedir + File.separator + System.currentTimeMillis() + "_" + fileName);
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = in.read(bytes)) != -1) {
				out.write(bytes);
			}
		}catch(Exception e) {
			logger.error(ThrowableParser.toString(e));
		}finally{
			Closer.close(out);
		}
		return file;
	}
}

package com.example.BeatyPhoneServer.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class ParamChangUtil {
	public static String getValue(HttpServletRequest request, String key){
		String value;
		try {
			value = new String(request.getParameter(key).getBytes(
					"iso-8859-1"), "UTF-8");
			return value;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static byte[] getByteValue(HttpServletRequest request){
		ServletInputStream servletInputStream;
		try {
			servletInputStream = request.getInputStream();
			ByteArrayOutputStream  out = new ByteArrayOutputStream ();  
			byte[] b = new byte[1024];  
			int i = 0;  
			while((i = servletInputStream.read(b,0,1024))>0){  
				out.write(b,0,i);  
			}  
			return out.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
	}
}

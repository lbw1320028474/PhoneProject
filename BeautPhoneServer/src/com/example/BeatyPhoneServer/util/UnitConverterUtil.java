package com.example.BeatyPhoneServer.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UnitConverterUtil {

	public static final byte[] input2byte(InputStream inStream)  
	{  
		try {
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
			byte[] buff = new byte[100];  
			int rc = 0;  
			while ((rc = inStream.read(buff, 0, 100)) > 0) {  
				swapStream.write(buff, 0, rc);  
			}  
			byte[] in2b = swapStream.toByteArray();  
			return in2b;  
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}


}

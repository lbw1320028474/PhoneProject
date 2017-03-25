package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;

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

	public static int px2dip(Context context, float pxValue) {  
		final float scale = context.getResources().getDisplayMetrics().density;  
		return (int) (pxValue / scale + 0.5f);  
	}  

	/** 
	 * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С���� 
	 *  
	 * @param dipValue 
	 * @param scale 
	 *            ��DisplayMetrics��������density�� 
	 * @return 
	 */  
	public static int dip2px(Context context, float dipValue) {  
		final float scale = context.getResources().getDisplayMetrics().density;  
		return (int) (dipValue * scale + 0.5f);  
	}  

	/** 
	 * ��pxֵת��Ϊspֵ����֤���ִ�С���� 
	 *  
	 * @param pxValue 
	 * @param fontScale 
	 *            ��DisplayMetrics��������scaledDensity�� 
	 * @return 
	 */  
	public static int px2sp(Context context, float pxValue) {  
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
		return (int) (pxValue / fontScale + 0.5f);  
	}  

	/** 
	 * ��spֵת��Ϊpxֵ����֤���ִ�С���� 
	 *  
	 * @param spValue 
	 * @param fontScale 
	 *            ��DisplayMetrics��������scaledDensity�� 
	 * @return 
	 */  
	public static int sp2px(Context context, float spValue) {  
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
		return (int) (spValue * fontScale + 0.5f);  
	}  

}

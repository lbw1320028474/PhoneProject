package com.example.util;

import java.security.MessageDigest;

public class MD5Util {
	private static final char HEX[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * MD5加密
	 * 
	 * @param contents
	 *            要加密的内容
	 * @return 加密后的内容
	 */
	public final static String md5(String content) {
		if (content == null || content.equals("")) // 不可加密�?
			return null;
		try {
			byte[] btInput = content.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = HEX[byte0 >>> 4 & 0xf];
				str[k++] = HEX[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

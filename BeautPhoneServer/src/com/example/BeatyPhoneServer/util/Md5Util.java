package com.example.BeatyPhoneServer.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {
	 public final static String encode(String data) {
		 try {
			return DigestUtils.md5Hex(data.getBytes("UTF-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	 }
	 
}

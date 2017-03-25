package com.example.beautyphone;

import com.squareup.okhttp.OkHttpClient;

public class OkHttpClientInstance {
	private OkHttpClientInstance(){};
	private static OkHttpClient okHttpClientInstance;
	public static OkHttpClient getInstance(){
		if(okHttpClientInstance == null){
			okHttpClientInstance = new OkHttpClient();
		}
		return okHttpClientInstance;
	}
}

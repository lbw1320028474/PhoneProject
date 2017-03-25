package com.example.util;

import java.io.IOException;

import com.example.beautyphone.OkHttpClientInstance;
import com.example.beautyphone.UrlManager;
import com.example.contacts.CardLocationBean;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NumberLocationUtil {
	public void getNumberLocation(final String number, final Handler handler){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				CardLocationBean bean = new CardLocationBean();
				OkHttpClient client = OkHttpClientInstance.getInstance();
				//Log.e("c", UrlManager.getNumberLocationUrl(number));
				Request request = new Request.Builder().url(UrlManager.getNumberLocationUrl(number.replace(" ", ""))).build();
				Response response;
				Log.e("c", "开始请求归属地");
				try {
					Message message = new Message();
					Bundle bundle = new Bundle();
					response = client.newCall(request).execute();
					if (response.isSuccessful()) {
						//	Toast.makeText(MainActivity.this, response.body().string() , 0).show();\
						String simInfoXml = response.body().string();
						Log.e("c", simInfoXml);
						int start = simInfoXml.indexOf(number);
						int end = simInfoXml.lastIndexOf("</string>");
						if(start >= 0 && end >= 0){
							String simInfo = simInfoXml.substring(start, end);
							int dIndex = simInfo.indexOf("：");
							int startSpace = simInfo.indexOf(" ");
							int endSpace = simInfo.lastIndexOf(" ");
							if(dIndex >= 0 && startSpace >= 0 && endSpace >= 0){
								bean.setNumber(number);
								bean.setProvince(simInfo.substring(dIndex + 1, startSpace));
								bean.setCity(simInfo.substring(startSpace + 1, endSpace));
								String simDiscrib = simInfo.substring(endSpace + 1, simInfo.length()).replace(bean.getProvince(), "");
								bean.setSimType(simDiscrib.substring(0, 2));
							}
							Log.e("c", bean.toString());
							bundle.putSerializable("simBean", bean);
						}
					}else{
						//Toast.makeText(MainActivity.this, "运行异常" , 0).show();;
						bundle.putSerializable("simBean", bean);
						throw new IOException("Unexpected code " + response);
					}
					message.setData(bundle);
					handler.sendMessage(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}

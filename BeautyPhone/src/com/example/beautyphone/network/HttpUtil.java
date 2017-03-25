package com.example.beautyphone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.example.beautyphone.network.Bean.BaseRequest;
import com.example.beautyphone.network.Bean.UserLoginResponse;
import com.example.util.UnitConverterUtil;
import com.skymobi.pay.tlv.TLVCodeUtil;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpUtil {
	private static HttpUtil httpUtil = null;
	private static OkHttpClient okHttpClient = null;
	private HttpUtil(){}

	public static HttpUtil getInstance(){
		if(httpUtil == null){
			okHttpClient = new OkHttpClient();
			httpUtil = new HttpUtil();
		}
		return httpUtil;
	}

	public void okHttpDoPost(final String request_url, final BaseRequest request, final Handler handler){
		/*Request multipart = buildMultipartFormRequest(
				request_url, null, null, null);*/
		RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), TLVCodeUtil.encode(request));
		Request request2 = new Request.Builder()
				.url(request_url)
				.post(body)
				.build();
		Call call = okHttpClient.newCall(request2);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				byte[] dataByte = arg0.body().bytes();
				Message message = new Message();
				message.what = MessageWhat.LOGIN_MESSAGE_WHAT;
				Bundle bundle = new Bundle();
				bundle.putByteArray("byte", dataByte);
				message.setData(bundle);
				handler.sendMessage(message);
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = MessageWhat.LOGIN_MESSAGE_WHAT;
				Bundle bundle = new Bundle();
				message.setData(bundle);
				handler.sendMessage(message);
			}
		});
	}

	public void okHttpDoGet(final String request_url, final Handler handler){
		/*Request multipart = buildMultipartFormRequest(
				request_url, null, null, null);*/
		Request request2 = new Request.Builder()
				.url(request_url)
				.build();
		Call call = okHttpClient.newCall(request2);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				String dataByte = arg0.body().string();
				Message message = new Message();
				message.what = MessageWhat.LOGIN_MESSAGE_WHAT;
				Bundle bundle = new Bundle();
				bundle.putString("json", dataByte);
				message.setData(bundle);
				handler.sendMessage(message);
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = MessageWhat.LOGIN_MESSAGE_WHAT;
				Bundle bundle = new Bundle();
				bundle.putString("json", null);
				message.setData(bundle);
				handler.sendMessage(message);
			}
		});
	}

	public void doPost(final String request_url, final BaseRequest request, final Handler handler){
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.what = MessageWhat.LOGIN_MESSAGE_WHAT;
				Bundle bundle = new Bundle();
				try {
					byte[] entitydata = TLVCodeUtil.encode(request);
					URL url = new URL(request_url);
					Log.e("c", request_url);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setConnectTimeout(10000);
					conn.setDoOutput(true);
					OutputStream outStream = conn.getOutputStream();
					outStream.write(entitydata);
					outStream.flush();
					outStream.close();
					if (conn.getResponseCode() == 200) {
						InputStream iStream = conn.getInputStream();
						byte[] dataByte = UnitConverterUtil.input2byte(iStream);
						bundle.putByteArray("byte", dataByte);
						message.setData(bundle);
						handler.sendMessage(message);
					}
				} catch (Exception e) {
					// TODO: handle exception
					message.setData(bundle);
					handler.sendMessage(message);
				}
			}
		}).start();
	}
}

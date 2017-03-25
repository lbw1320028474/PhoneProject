package com.example.BeatyPhoneServer.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.framed.Header;


public class HttpUtil {
	protected static final int DEFAULT_CONNECTION_TIME_OUT = 1000;
	public static final String system_error_result = "-99";
	private static OkHttpClient okHttpClient = null;

	public static String excute(HttpMethod method) {

		HttpClient client = new HttpClient();

		method.setRequestHeader("Connection", "close");

		method.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");

		// method.setRequestHeader("Time", "yyyyMMddHHmmss");
		// method.setRequestHeader("Encrypt", "MD5(username+pass+time)");
		try {
			client.setTimeout(DEFAULT_CONNECTION_TIME_OUT);
			client.executeMethod(method);

			return method.getResponseBodyAsString();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return "";
	}

	/**
	 * 使用 POST 方式提交数据
	 *
	 * @return
	 */
	private static HttpMethod getPostMethod(String url,
			List<NameValuePair> pairs) {
		PostMethod post = new PostMethod(url);
		NameValuePair[] array = new NameValuePair[pairs.size()];
		post.setRequestBody(pairs.toArray(array));
		return post;
	}

	public static String doPost(String url, List<NameValuePair> pairs){
		return excute(getPostMethod(url, pairs));
	}


	public static String doGet(String request_url){
		try {
			URL url = new URL(request_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(10000);
			if (conn.getResponseCode() == 200) {
				InputStream iStream = conn.getInputStream();
				byte[] dataByte = UnitConverterUtil.input2byte(iStream);
				if(dataByte != null){
					String dataStr = new String(dataByte);
					return dataStr;
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return null;
	}

	/*	public void okHttpDoPost(final String request_url, final BaseRequest request, final Handle handle){
		Request multipart = buildMultipartFormRequest(
				request_url, null, null, null);
		//RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), TLVCodeUtil.encode(request));
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
				Handler.sendMessage(message);
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
	}*/

	/*public void doPost(final String request_url, final BaseRequest request, final Handler handler){
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
						handler.;
					}
				} catch (Exception e) {
					// TODO: handle exception
					message.setData(bundle);
					handler.sendMessage(message);
				}
			}
		}).start();
	}*/
}

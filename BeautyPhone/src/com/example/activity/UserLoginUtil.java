package com.example.activity;

import com.alibaba.fastjson.JSONObject;
import com.example.beautyphone.BroadCaseReceiverFilter;
import com.example.beautyphone.UrlManager;
import com.example.beautyphone.FYSdk.FYAppData;
import com.example.beautyphone.network.HttpUtil;
import com.example.beautyphone.network.Bean.UserLoginRequest;
import com.example.beautyphone.network.Bean.UserLoginResponse;
import com.example.util.MD5Util;
import com.example.util.ShareDpreferenceUtil;
import com.feiyucloud.sdk.FYClient;
import com.feiyucloud.sdk.FYClientListener;
import com.feiyucloud.sdk.FYError;
import com.skymobi.pay.tlv.TLVCodeUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UserLoginUtil {
	private boolean userIsLogin = false;
	private String userPassword = "";
	private loginListener listener = null;
	private static UserLoginUtil loginUtil = null;
	private UserLoginResponse loginResponse = null;
	private static Context context = null;
	private UserLoginUtil(){

	}

	public static UserLoginUtil getInstance(Context context){
		UserLoginUtil.context = context;
		if(loginUtil == null){
			loginUtil = new UserLoginUtil();
		}
		return loginUtil;
	}

	/**
	 * 
	 * @param userAccount
	 * @param userPassword
	 * @param identy
	 * @param listener
	 */
	public void login(String userAccount, String userPassword, String identy, final loginListener listener){
		this.listener = listener;
		this.userPassword = userPassword;
		UserLoginRequest request = new UserLoginRequest();
		request.setUserName(userAccount);
		request.setUserPassowrd(userPassword);
		request.setIdentifyCode(identy);
		HttpUtil.getInstance().okHttpDoPost(UrlManager.LOGIN_URL, request, new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				try {
					byte[] data = msg.getData().getByteArray("byte");
					if(data != null){
						loginResponse = TLVCodeUtil.decode(data, UserLoginResponse.class);
						if(loginResponse != null && loginResponse.getLoginState() == 1){
							//Toast.makeText(getActivity(), "登录成功", 0).show();
							Log.e("c", loginResponse.toString());
							loginFYsdk(loginResponse);
						}else if(loginResponse == null){
							userIsLogin = false;
							UserLoginUtil.this.listener.loginState(4, "未知错误");
						}else if(loginResponse != null && loginResponse.getLoginState() == 2){
							userIsLogin = false;
							UserLoginUtil.this.listener.loginState(2, "用户不存在");
						}else if(loginResponse != null && loginResponse.getLoginState() == 0){
							userIsLogin = false;
							UserLoginUtil.this.listener.loginState(0, "密码错误");
						}else{
							userIsLogin = false;
							UserLoginUtil.this.listener.loginState(2, "用户不存在");
						}
					}else{
						userIsLogin = false;
						UserLoginUtil.this.listener.loginState(4, "未知错误");
					}
				} catch (Exception e) {
					// TODO: handle exception
					userIsLogin = false;
					UserLoginUtil.this.listener.loginState(5, "网络线路问题");
				}
			}
		});
	}

	public interface loginListener{
		/**
		 * 
		 * @param i：0-密码错误，1-登录成功，2-用户不存在，3-飞语sdk登录失败,4-未知错误,5-网络线路问题
		 * @param message
		 */
		public void loginState(int i, String message);
	}

	private void loginFYsdk(UserLoginResponse loginResponse) {
		// TODO Auto-generated method stub
		FYClient.addListener(clientListener);
		String account = loginResponse.getFyAccount();
		String password = loginResponse.getFyPassword();
		FYClient.instance().connect(FYAppData.getAPPID(),FYAppData.getAPPTOKEN(), account, password);
	}

	private FYClientListener clientListener = new FYClientListener() {

		@Override
		public void onConnectionSuccessful() {
			// TODO Auto-generated method stub
			Log.e("c", "链接成功");
			saveLoginData(loginResponse);
		}

		@Override
		public void onConnectionFailed(FYError arg0) {
			// TODO Auto-generated method stub
			Log.e("c", "链接失败");
			userIsLogin = false;
			listener.loginState(3, arg0.msg);
		}
	}; 

	public void saveLoginData(UserLoginResponse loginResponse2) {
		// TODO Auto-generated method stub
		String userJson = JSONObject.toJSONString(loginResponse2);
		ShareDpreferenceUtil dpreferenceUtil = new ShareDpreferenceUtil();
		if(!userJson.isEmpty()){
			dpreferenceUtil.setShareData(context, "userInfo", userJson);
			dpreferenceUtil.setShareData(context, "userPassword", this.userPassword);
			Log.e("c", userPassword + " + " + "正在储存密码\n" + userJson);
			Intent i = new Intent(BroadCaseReceiverFilter.LOGIN_SUCCESS_FILTER);
			context.sendBroadcast(i);
			userIsLogin = true;
			listener.loginState(1, "登录成功");
		}
	}

	public boolean isUserIsLogin() {
		return userIsLogin;
	}

	public void setUserIsLogin(boolean userIsLogin) {
		this.userIsLogin = userIsLogin;
	}

	public void loginAute(){
		loginResponse = new UserLoginResponse();
		ShareDpreferenceUtil dpreferenceUtil = new ShareDpreferenceUtil();
		String userInfo = dpreferenceUtil.getShareData(context, "userInfo");
		String userPassword = dpreferenceUtil.getShareData(context, "userPassword");
		Log.e("c", userInfo + " + " + userPassword + "自动登录");
		if(userInfo.isEmpty()){
			return;
		}
		JSONObject jsonObject = JSONObject.parseObject(userInfo);
		loginResponse = JSONObject.toJavaObject(jsonObject, UserLoginResponse.class);
		login(loginResponse.getUserAccount(), userPassword, 0 + "", new loginListener() {

			@Override
			public void loginState(int i, String message) {
				// TODO Auto-generated method stub
				Log.e("c", i + " + " + message);
			}
		});
	}



	public UserLoginResponse getLoginUserInfo(){
		if(userIsLogin){
			ShareDpreferenceUtil dpreferenceUtil = new ShareDpreferenceUtil();
			String userInfo = dpreferenceUtil.getShareData(context, "userInfo");
			JSONObject jsonObject = JSONObject.parseObject(userInfo);
			UserLoginResponse loginResponse = JSONObject.toJavaObject(jsonObject, UserLoginResponse.class);
			return loginResponse;
		}else{
			return null;
		}
	}

}

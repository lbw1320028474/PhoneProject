package com.example.fragment;

import java.util.Timer;
import java.util.TimerTask;

import com.example.activity.LoginActivity;
import com.example.activity.UserLoginUtil;
import com.example.activity.UserLoginUtil.loginListener;
import com.example.beautyphone.R;
import com.example.beautyphone.network.Bean.UserLoginRequest;
import com.example.beautyphone.network.Bean.UserLoginResponse;
import com.example.myDialog.LoadingDialog;
import com.example.util.MD5Util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment implements OnClickListener{
	private EditText userName = null;
	private EditText password = null;
	private TextView fogortPassword = null;
	private Button loginButton = null;
	private UserLoginRequest loginRequest = null;
	private UserLoginResponse loginResponse = null;
	private LoadingDialog loadingDialog = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.login_fragment_layout, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		loadingDialog = new LoadingDialog(getActivity());
		userName = (EditText)view.findViewById(R.id.loginfragment_account_edittext);
		password = (EditText)view.findViewById(R.id.loginfragment_password_edittext);
		fogortPassword = (TextView)view.findViewById(R.id.loginfragment_fogetpassword);
		loginButton = (Button)view.findViewById(R.id.loginfragment_login_button);
		loginButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.loginfragment_login_button:
			login();
			break;

		case R.id.loginfragment_fogetpassword:
			Toast.makeText(getActivity(), "忘记密码了", 0).show();;
			break;
		}
	}

	private void login() {
		// TODO Auto-generated method stub
		String account_str = userName.getText().toString();
		String password_str =password.getText().toString();
		if(account_str.isEmpty()){
			Toast.makeText(getActivity(), "号码不能为空", 0).show();
			return;
		}
		if(password_str.isEmpty()){
			Toast.makeText(getActivity(), "密码不能为空", 0).show();
			return;
		}
		loadingDialog.showDialog();
		UserLoginUtil.getInstance(getActivity()).login(account_str, MD5Util.md5(password_str), "0", listener);
		/*loginRequest = new UserLoginRequest();
		loginRequest.setUserName(account_str);
		loginRequest.setUserPassowrd(MD5Util.md5(password_str));
		loginRequest.setIdentifyCode(0 + "");*/
		//HttpUtil.getInstance().okHttpDoPost(UrlManager.LOGIN_URL, loginRequest, handler);
	}

	private loginListener listener = new loginListener() {

		@Override
		public void loginState(int i, String message) {
			// TODO Auto-generated method stub
			loadingDialog.closeDialog();
			if(i == 1){
				Toast.makeText(getActivity(), "登录成功", 0).show();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						getActivity().finish();
					}
				}, 1500);
			}else{
				Toast.makeText(getActivity(), "登录失败," + message, 0).show();
			}
		}
	};

	/*	private Handler handler = new Handler(){
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
					}else if(loginResponse != null){
						Toast.makeText(getActivity(), "登录获取数据失败" + loginResponse.getLoginMessage(), 0).show();
					}else if(loginResponse != null && loginResponse.getLoginState() != 1){
						Toast.makeText(getActivity(), "登录失败" + loginResponse.getLoginMessage(), 0).show();
					}else{
						Toast.makeText(getActivity(), "登录失败" + loginResponse.getLoginMessage(), 0).show();
					}
				}else{
					Toast.makeText(getActivity(), "登录获取byte失败" + loginResponse.getLoginMessage(), 0).show();
				}
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getActivity(), "服务器被偷了！", 0).show();
			}
		}

	};*/

	/*	private void loginFYsdk(UserLoginResponse loginResponse) {
		// TODO Auto-generated method stub
		FYClient.addListener(clientListener);
		String account = loginResponse.getFyAccount();
		String password = loginResponse.getFyPassword();
		FYClient.instance().connect(FYAppData.getAPPID(),FYAppData.getAPPTOKEN(), account, password);
	}
	 */
	/*	private FYClientListener clientListener = new FYClientListener() {

		@Override
		public void onConnectionSuccessful() {
			// TODO Auto-generated method stub
			Log.e("c", "链接成功");
			Toast.makeText(getActivity(), "登录成功", 0).show();
			initData(loginResponse);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					getActivity().finish();
				}
			}, 3000);
		}

		@Override
		public void onConnectionFailed(FYError arg0) {
			// TODO Auto-generated method stub
			Log.e("c", "链接失败");
		}
	}; */

	/*
	private void initData(UserLoginResponse loginResponse2) {
		// TODO Auto-generated method stub
		String userJson = JSONObject.toJSONString(loginResponse2);
		ShareDpreferenceUtil dpreferenceUtil = new ShareDpreferenceUtil();
		if(!userJson.isEmpty()){
			dpreferenceUtil.setShareData(getActivity(), "userInfo", userJson);
			Intent i = new Intent("con.personal.infochange");
			getActivity().sendBroadcast(i);
		}
	}*/
}

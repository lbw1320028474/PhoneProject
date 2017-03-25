package com.example.activity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.beautyphone.BroadCaseReceiverFilter;
import com.example.beautyphone.R;
import com.example.beautyphone.network.Bean.UserLoginResponse;
import com.example.contacts.CardLocationBean;
import com.example.contacts.NewCallLogBean;
import com.example.db.ContactsUtil;
import com.example.util.NumberLocationUtil;
import com.example.util.ShareDpreferenceUtil;
import com.feiyucloud.sdk.FYCall;
import com.feiyucloud.sdk.FYCallListener;
import com.feiyucloud.sdk.FYError;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IsCallIngActivity extends Activity implements OnClickListener, FYCallListener{
	public static NewCallLogBean bean = null;
	private String callNumber = "";
	private String callName = "";
	private String simType = "";
	private int callType = -1;

	private int noSoundState = 0;
	private int bigSoundState = 0;
	private int saveSoundState = 0;
	private int callBackState = 0;
	private int closeState = 0;
	private int openBoardState = 0;

	private LinearLayout noSoundLayout = null;
	private LinearLayout bigSoundLayout = null;
	private LinearLayout saveSoundLayout = null;
	private LinearLayout backCallLayout = null;
	private LinearLayout closeCallLayout = null;
	private LinearLayout numberBoardLayout = null;

	private ImageView fromConver = null;
	private ImageView toConver = null;
	private TextView callStateTip = null;
	private ImageView isCallingAnim = null;
	private TextView callTip = null;
	private TextView callContactName = null;
	private TextView callContactNumber = null;

	private int callSecond = 0;
	private int callMin = 0;
	private int hour = 0;
	private boolean callIsSuccess = false;
	public static Uri newCallLogUri = null;

	private Timer callTimer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.is_calling_activity_layout);
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		if(callNumber.isEmpty()){
			return;
		}
		beginToCall();
		if(!callName.isEmpty()){
			callContactName.setText(callName);
			callContactNumber.setText(callNumber + "(" + simType + ")");
		}else{
			callContactName.setText(callNumber);
			callContactNumber.setText(simType);
		}

		/*Intent intent = this.getIntent();
		callNumber = intent.getStringExtra("number");
		Log.e("c", callNumber);*/
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		isCallingAnim.setImageResource(R.drawable.directcall_anim);
		AnimationDrawable animationDrawable = (AnimationDrawable) isCallingAnim.getDrawable();
		animationDrawable.start();
	}

	private void beginToCall() {
		FYCall.addListener(this);
		Log.e("c", "开始直拨");
		addCallLog();
		if(callType == 0){
			if(callNumber.length() == 11){
				//在此做直拨请求
				callIsSuccess = false;
				String truecallNumber = callNumber.replace("+86", "");
				truecallNumber = truecallNumber.replace(" ", "");
				FYCall.instance().directCall(truecallNumber, FYCall.SHOW_NUMBER_ON, false, null);
			}else{
				callTip.setText("请输入11位手机号码");
				callStateTip.setText("未呼出");
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						finish();
					}
				}, 3000);
			}
		}
	}

	private void addCallLog() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				bean = new NewCallLogBean();
				bean.setType(2);
				bean.setDate(Calendar.getInstance().getTimeInMillis() + "");
				bean.setDuration(0);
				bean.setGeocoded_location(simType);
				bean.setName(callName);
				bean.setNumber(callNumber);
				bean.setCountryiso("CN");
				Log.e("c", bean.toString());
				newCallLogUri= ContactsUtil.addCallLog(IsCallIngActivity.this, bean);
				Intent i = new Intent(BroadCaseReceiverFilter.CALLLOG_UPDATAED);
				IsCallIngActivity.this.sendBroadcast(i);
				//Log.e("c", uri.toString());
				/*if(ContactsUtil.getCallLogByUri(uri) != null){
					Log.e("c", ContactsUtil.getCallLogByUri(uri).toString());
				}*/                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
			}
		}).start();
	}

	private void updataCallLog() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				bean.setType(2);
				bean.setGeocoded_location(simType);
				int duration = hour * 60 * 60 + callMin * 60 + callSecond;
				bean.setDuration(duration);
				Log.e("c", bean.toString());
				int row= ContactsUtil.updataCallLog(IsCallIngActivity.this, bean);
				if(row <= 0){
					return;
				}
				Intent i = new Intent(BroadCaseReceiverFilter.CALLLOG_UPDATAED);
				IsCallIngActivity.this.sendBroadcast(i);
				/*
				dpreferenceUtil.setShareData(IsCallIngActivity.this, "userInfo", userJson);
				Intent i = new Intent(BroadCaseReceiverFilter.PAY_SUCCESS_FILTER);
				this.sendBroadcast(i);*/
				//Log.e("c", uri.toString());
				/*if(ContactsUtil.getCallLogByUri(uri) != null){
					Log.e("c", ContactsUtil.getCallLogByUri(uri).toString());
				}*/                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
			}
		}).start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	private void initView() {
		// TODO Auto-generated method stub
		Intent intent = this.getIntent();
		String jsonObjects = intent.getStringExtra("callData");
		if(jsonObjects != null && !jsonObjects.equals("")){
			try {
				JSONObject jsonObject = JSONObject.parseObject(jsonObjects);
				String name = jsonObject.getString("name");
				if(name != null && !name.equals("")){
					callName = name;
				}
				String number = jsonObject.getString("number");
				if(number != null && !number.equals("")){
					callNumber = number;
				}
				if(callNumber.isEmpty()){
					FinshByDeal();
					return;
				}
				String simtype = jsonObject.getString("simType");
				if(simType.isEmpty() || simType.equals("未知 归属地")){
					NumberLocationUtil locationUtil = new NumberLocationUtil();
					String opterNumber = callNumber;
					opterNumber = opterNumber.replace(" ", "");
					opterNumber = opterNumber.replace("+86", "");
					locationUtil.getNumberLocation(opterNumber, locationHandle);
				}else{
					simType = simtype;
				}
				callType = jsonObject.getIntValue("callType");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			FinshByDeal();
		}
		callTimer = new Timer();
		fromConver = (ImageView)this.findViewById(R.id.iscalling_fromcall_cover);
		toConver = (ImageView)this.findViewById(R.id.iscalling_tocall_cover);
		callStateTip = (TextView)this.findViewById(R.id.iscalling_callstatetext);
		isCallingAnim = (ImageView)this.findViewById(R.id.iscalling_direct_imageanim);
		callTip = (TextView)this.findViewById(R.id.iscalling_calltip);
		callContactName = (TextView)this.findViewById(R.id.iscalling_name);
		callContactNumber = (TextView)this.findViewById(R.id.iscalling_number);

		noSoundLayout = (LinearLayout)this.findViewById(R.id.iscalling_nosound_button);
		bigSoundLayout = (LinearLayout)this.findViewById(R.id.iscalling_bigsound_button);
		saveSoundLayout = (LinearLayout)this.findViewById(R.id.iscalling_savesound_button);
		backCallLayout = (LinearLayout)this.findViewById(R.id.iscalling_backcall_button);
		closeCallLayout = (LinearLayout)this.findViewById(R.id.iscalling_closecall_button);
		numberBoardLayout = (LinearLayout)this.findViewById(R.id.iscalling_openboard_button);
		noSoundLayout.setOnClickListener(this);
		bigSoundLayout.setOnClickListener(this);
		saveSoundLayout.setOnClickListener(this);
		backCallLayout.setOnClickListener(this);
		closeCallLayout.setOnClickListener(this);
		numberBoardLayout.setOnClickListener(this);
	}

	private void FinshByDeal() {
		if(IsCallIngActivity.this != null){
			finish();
		}
	}

	private Handler locationHandle = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle bundle = msg.getData();
			if(bundle != null){
				CardLocationBean cardBean = (CardLocationBean)bundle.getSerializable("simBean");
				if(cardBean != null){
					IsCallIngActivity.this.simType = cardBean.getProvince() + cardBean.getCity() + " " + cardBean.getSimType();
				}else{
					IsCallIngActivity.this.simType = "未知归属地";
				}
			}else{
				IsCallIngActivity.this.simType = "未知归属地";
			}
			if(!callName.isEmpty()){
				callContactNumber.setText(callNumber + "(" + IsCallIngActivity.this.simType + ")");
			}else{
				callContactNumber.setText(IsCallIngActivity.this.simType);
			}
			updataCallLog();
		};
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iscalling_nosound_button:
			if(FYCall.instance().isMuted()){
				FYCall.instance().setMuteEnabled(false);
				noSoundState = 0;
				noSoundLayout.setBackground(IsCallIngActivity.this.getResources().getDrawable(R.drawable.a_iscalling_otherbut_normalbg));
			}else{
				FYCall.instance().setMuteEnabled(true);
				noSoundState = 1;
				noSoundLayout.setBackground(IsCallIngActivity.this.getResources().getDrawable(R.drawable.a_iscalling_otherbut_chosedbg));
			}
			break;
		case R.id.iscalling_bigsound_button:
			if(FYCall.instance().isSpeakerEnabled()){
				FYCall.instance().setSpeakerEnabled(false);
				bigSoundLayout.setBackground(IsCallIngActivity.this.getResources().getDrawable(R.drawable.a_iscalling_otherbut_normalbg));
				bigSoundState = 0;
			}else{
				FYCall.instance().setSpeakerEnabled(true);
				bigSoundLayout.setBackground(IsCallIngActivity.this.getResources().getDrawable(R.drawable.a_iscalling_otherbut_chosedbg));
				bigSoundState = 1;
			}
			break;
		case R.id.iscalling_savesound_button:
			break;
		case R.id.iscalling_backcall_button:
			break;
		case R.id.iscalling_closecall_button:
			/*QMYCall.hangUp();*/
			callStateTip.setText("正在挂断");
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(IsCallIngActivity.this != null){
						finish();
					}
				}
			}, 2000);
			FYCall.instance().endCall();
			break;
		case R.id.iscalling_openboard_button:

			break;
		}
	}

	@Override
	public void onCallAlerting(String arg0){
		// TODO Auto-generated method stub
		callStateTip.setText("正在响铃");
		Toast.makeText(IsCallIngActivity.this, "onCallAlerting:" + arg0, 0).show();
		FYCall.instance().setMuteEnabled(false);
		FYCall.instance().setSpeakerEnabled(false);
	}

	@Override
	public void onCallEnd() {
		// TODO Auto-generated method stub
		if(callIsSuccess){
			UserLoginResponse loginResponse = new UserLoginResponse();
			ShareDpreferenceUtil dpreferenceUtil = new ShareDpreferenceUtil();
			String userInfo = dpreferenceUtil.getShareData(IsCallIngActivity.this, "userInfo");
			if(userInfo.isEmpty()){
				return;
			}
			JSONObject jsonObject = JSONObject.parseObject(userInfo);
			loginResponse = JSONObject.toJavaObject(jsonObject, UserLoginResponse.class);
			int callTime = 0;
			if(callSecond > 30){
				callTime = callTime + callMin + hour * 60 + 1;
			}else{
				callTime = callTime + callMin + hour * 60;
			}
			loginResponse.setMaxPhoneTime((Integer.parseInt(loginResponse.getMaxPhoneTime()) - callTime) + "");
			callIsSuccess = false;
			updataCallLog();
			String userJson = JSONObject.toJSONString(loginResponse);
			if(!userJson.isEmpty()){
				dpreferenceUtil.setShareData(IsCallIngActivity.this, "userInfo", userJson);
				Intent i = new Intent(BroadCaseReceiverFilter.PAY_SUCCESS_FILTER);
				this.sendBroadcast(i);
			}
		}
		callTimer.cancel();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(IsCallIngActivity.this != null){
					finish();
				}
			}
		}, 2000);
	}

	@Override
	public void onCallFailed(FYError arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(IsCallIngActivity.this, "onCallFailed:" + arg0.toString(), 0).show();
	}

	@Override
	public void onCallRunning(String arg0) {
		// TODO Auto-generated method stub
		callIsSuccess = true;
		Toast.makeText(IsCallIngActivity.this, "onCallRunning:" + arg0, 0).show();
		callTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				callSecond = callSecond + 1;
				if(callSecond > 60){
					callMin+=1;
					callSecond = 0;
					if(callMin > 60){
						callMin = 0;
						hour+=1;
					}
				}
				runOnUiThread(new Runnable() {
					public void run() {
						UpDate();
					}
				});
			}

		}, 1000, 1000);
	}

	private void UpDate() {
		String se = "";
		String mi = "";
		String ho = "";
		if(callSecond < 10){
			se = "0" + callSecond;
		}else{
			se =callSecond + "";
		}
		if(callMin < 10){
			mi = "0" + callMin;
		}else{
			mi = "" + callMin;
		}
		if(hour < 10){
			ho = "0" + hour;
		}else{
			ho = hour + "";
		}
		callStateTip.setText(ho + ":" + mi + ":" + se);
	}

	@Override
	public void onCallbackFailed(FYError arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(IsCallIngActivity.this, "onCallbackFailed:" + arg0.toString(), 0).show();
	}

	@Override
	public void onCallbackSuccessful() {
		// TODO Auto-generated method stub
		Toast.makeText(IsCallIngActivity.this, "onCallbackSuccessful:", 0).show();
	}

	@Override
	public void onDtmfReceived(char arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(IsCallIngActivity.this, "onDtmfReceived:" + arg0, 0).show();
	}

	@Override
	public void onIncomingCall(String arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(IsCallIngActivity.this, "onIncomingCall:" + arg0, 0).show();
	}

	@Override
	public void onOutgoingCall(String arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(IsCallIngActivity.this, "onOutgoingCall:" + arg0, 0).show();
	}
}

package com.example.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;
import com.example.beautyphone.BroadCaseReceiverFilter;
import com.example.beautyphone.R;
import com.example.beautyphone.UrlManager;
import com.example.beautyphone.network.HttpUtil;
import com.example.beautyphone.network.Bean.MyOrderRequest;
import com.example.beautyphone.network.Bean.MyOrderResponse;
import com.example.beautyphone.network.Bean.UserLoginResponse;
import com.example.util.ShareDpreferenceUtil;
import com.skymobi.pay.tlv.TLVCodeUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import c.b.BP;
import c.b.PListener;

public class PayCenter extends Activity implements OnClickListener{
	private MyOrderRequest myOrderRequest = null;

	private ImageView back_button = null;
	private TextView userNameTextview = null;
	private TextView blackMoney = null;
	private TextView addMoney = null;
	private UserLoginResponse loginResponse = null;

	private LinearLayout pay_5_yuan_button = null;
	private LinearLayout pay_10_yuan_button = null;
	private LinearLayout pay_20_yuan_button = null;
	private LinearLayout pay_30_yuan_button = null;
	private LinearLayout pay_50_yuan_button = null;
	private LinearLayout pay_100_yuan_button = null;
	private LinearLayout pay_200_yuan_button = null;
	private LinearLayout pay_500_yuan_button = null;

	private RadioGroup radioGroup = null;
	private RadioButton zhifubaoRadio = null;
	private RadioButton weichatRadio = null;
	private TextView pay_button = null;

	private float payMoney = 500;
	private float trueMoney = 500;
	private int payType = 1;		//1是支付宝，2是微信

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_center_layout);
		initView();
		initViewData();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		radioGroup.clearCheck();
		radioGroup.check(1);
		zhifubaoRadio.setChecked(true);
		payType = 1;
	}

	private void initViewData() {
		// TODO Auto-generated method stub

	}
	private void initView() {
		// TODO Auto-generated method stub
		back_button = (ImageView)this.findViewById(R.id.paycenter_back_button);
		userNameTextview = (TextView)this.findViewById(R.id.paycenter_useraccount);
		blackMoney = (TextView)this.findViewById(R.id.paycenter_black);
		addMoney = (TextView)this.findViewById(R.id.paycenter_true_addmoney);
		pay_5_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_5_yuan_button);
		pay_10_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_10_yuan_button);
		pay_20_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_20_yuan_button);
		pay_30_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_30_yuan_button);
		pay_50_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_50_yuan_button);
		pay_100_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_100_yuan_button);
		pay_200_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_200_yuan_button);
		pay_500_yuan_button = (LinearLayout)this.findViewById(R.id.paycenter_500_yuan_button);
		pay_button = (TextView)this.findViewById(R.id.paycenter_pay_button);
		radioGroup = (RadioGroup)this.findViewById(R.id.pay_type_radio_group);
		zhifubaoRadio = (RadioButton)this.findViewById(R.id.pay_type_radio_zhifubao);
		weichatRadio = (RadioButton)this.findViewById(R.id.pay_type_radio_weichat);
		back_button.setOnClickListener(this);
		pay_5_yuan_button.setOnClickListener(this);
		pay_10_yuan_button.setOnClickListener(this);
		pay_20_yuan_button.setOnClickListener(this);
		pay_30_yuan_button.setOnClickListener(this);
		pay_50_yuan_button.setOnClickListener(this);
		pay_100_yuan_button.setOnClickListener(this);
		pay_200_yuan_button.setOnClickListener(this);
		pay_500_yuan_button.setOnClickListener(this);
		pay_button.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.paycenter_5_yuan_button:
			pay_button.setText("充值 5元");
			addMoney.setText(" +5元");
			payMoney = 5;
			trueMoney = (float) 4.99;
			break;
		case R.id.paycenter_10_yuan_button:
			addMoney.setText(" +10元");
			pay_button.setText("充值 10元");
			payMoney = 10;
			trueMoney = (float) 9.98;
			break;
		case R.id.paycenter_20_yuan_button:
			pay_button.setText("充值 20元");
			addMoney.setText(" +20元");
			payMoney = 20;
			trueMoney = (float) 19.98;
			break;
		case R.id.paycenter_30_yuan_button:
			pay_button.setText("充值 30元");
			addMoney.setText(" +30元");
			payMoney = 30;
			trueMoney = (float) 29.9;
			break;
		case R.id.paycenter_50_yuan_button:
			pay_button.setText("充值 50元");
			addMoney.setText(" +50元");
			payMoney = 50;
			trueMoney = (float) 49.8;
			break;
		case R.id.paycenter_100_yuan_button:
			pay_button.setText("充值 100元");
			addMoney.setText(" +100元");
			payMoney = 100;
			trueMoney = (float) 99.6;
			break;
		case R.id.paycenter_200_yuan_button:
			addMoney.setText(" +200元");
			pay_button.setText("充值 200元");
			payMoney = 200;
			trueMoney = (float) 199;
			break;
		case R.id.paycenter_500_yuan_button:
			addMoney.setText(" +500元");
			pay_button.setText("充值 500元");
			payMoney = 500;
			trueMoney = (float) 498;
			break;
		case R.id.paycenter_pay_button:
			beginToPay();
			break;
		case R.id.paycenter_back_button:
			finish();
			break;
		}
	}

	private void beginToPay() {
		// TODO Auto-generated method stub

		boolean iszhifubao = true;
		if(zhifubaoRadio.isChecked()){
			iszhifubao = true;
		}else{
			iszhifubao = false;
		}
		if(!UserLoginUtil.getInstance(PayCenter.this).isUserIsLogin()){
			Toast.makeText(PayCenter.this, "用户未登录", 0).show();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(PayCenter.this, LoginActivity.class);
					startActivity(intent);
				}
			}, 2000);
			return;
		}
		ShareDpreferenceUtil share = new ShareDpreferenceUtil();
		String loginInfo = share.getShareData(PayCenter.this, "userInfo");
		if(loginInfo.isEmpty()){
			Toast.makeText(PayCenter.this, "用户数据错误", 0).show();
			return;
		}
		JSONObject jsonObject = JSONObject.parseObject(loginInfo);
		loginResponse = JSONObject.toJavaObject(jsonObject, UserLoginResponse.class);
		if(loginResponse == null){
			return;
		}
		String extrData = loginResponse.getUserAccount() + "_" + (payMoney + "").replace(".", "d");
		BP.pay("美天电话余额充值", extrData, 0.01, iszhifubao, new PListener() {

			@Override
			public void unknow() {
				// TODO Auto-generated method stub
				Log.e("c", "unknow未知");
			}

			@Override
			public void succeed() {
				// TODO Auto-generated method stub
				Log.e("c", "succeed支付成功");
				UserLoginResponse loginResponse = new UserLoginResponse();
				ShareDpreferenceUtil dpreferenceUtil = new ShareDpreferenceUtil();
				String userInfo = dpreferenceUtil.getShareData(PayCenter.this, "userInfo");
				if(userInfo.isEmpty()){
					return;
				}
				JSONObject jsonObject = JSONObject.parseObject(userInfo);
				loginResponse = JSONObject.toJavaObject(jsonObject, UserLoginResponse.class);
				loginResponse.setBalanceMoney((Float.parseFloat(loginResponse.getBalanceMoney()) + 
						Float.parseFloat(myOrderRequest.getPayMoney())) + "");
				String userJson = JSONObject.toJSONString(loginResponse);
				if(!userJson.isEmpty()){
					dpreferenceUtil.setShareData(PayCenter.this, "userInfo", userJson);
					Intent i = new Intent(BroadCaseReceiverFilter.PAY_SUCCESS_FILTER);
					PayCenter.this.sendBroadcast(i);
				}
			}

			@Override
			public void orderId(String arg0) {
				// TODO Auto-generated method stub
				Log.e("leileil", "orderIdID:" + arg0);
				myOrderRequest = new MyOrderRequest();
				myOrderRequest.setPayMoney(payMoney + "");
				myOrderRequest.setPayOrderId(arg0);
				myOrderRequest.setUserAccount(loginResponse.getUserAccount());
				myOrderRequest.setTruePayMoney(trueMoney + "");
				HttpUtil.getInstance().okHttpDoPost(UrlManager.SUBMIT_ORDER_URL, myOrderRequest, submitPoderHandl);
			}

			@Override
			public void fail(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("c", "fail:" + arg0 + " + " + arg1);
			}
		});
	}

	public Handler submitPoderHandl = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			byte[] dataByte = msg.getData().getByteArray("byte");
			if(dataByte != null){
				MyOrderResponse myOrderResponse = TLVCodeUtil.decode(dataByte, MyOrderResponse.class);
				if(myOrderResponse != null){
					Log.e("C", "订单提交成功:" + myOrderResponse.toString());
				}
			}
		}
	};
}

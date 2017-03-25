package com.example.BeatyPhoneServer.FYutil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.example.BeatyPhoneServer.Bean.FYAddAccountRequestBean;
import com.example.BeatyPhoneServer.Bean.FYAddAccountResultBean;
import com.example.BeatyPhoneServer.util.AppInfo;
import com.example.BeatyPhoneServer.util.HttpUtil;
import com.example.BeatyPhoneServer.util.Md5Util;


public class FYAction {
	private static FYAction action = null;
	private FYAction(){};

	public static FYAction getInstance(){
		if(action == null){
			action = new FYAction();
		}
		return action;
	}

	private static final String addAccount_url = "/api/addAccount";
	private static final String getAccount_url = "/api/getAccount";
	private static final String disableAccount_url = "/api/disableAccount";
	private static final String enableAccount_url = "/api/enableAccount";
	private static final String modifyAccountDisplayNumber_url = "/api/modifyAccountDisplayNumber";
	private static final String getAccountOnlineStatus_url = "/api/onlineStatus";
	private static final String callback_url = "/api/callback";
	private static final String getRecordDownUrl_url = "/api/getRecordDownUrl";

	private static final String sendSoundSms_url = "http://sms.feiyucloud.com/v2/sendSoundSms";

	private String FY_SERVER_IP = "api.feiyucloud.com";
	private String FY_SERVER_PORT = "80";

	public FYAddAccountRequestBean addAccount(String appAccountId, String globalMobilePhone, String district){
		String ti = System.currentTimeMillis() + "";
		String au = getAu(ti);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new NameValuePair("appAccountId", appAccountId));
		pairs.add(new NameValuePair("globalMobilePhone", globalMobilePhone));
		pairs.add(new NameValuePair("district", district));
		pairs.add(new NameValuePair("au", au));
		pairs.add(new NameValuePair("ti", ti));
		pairs.add(new NameValuePair("ver", AppInfo.APP_API_VERSION));
		pairs.add(new NameValuePair("appId", AppInfo.APP_ID));
		String result = HttpUtil.doPost(getFYUrl(addAccount_url), pairs);
		System.out.println(result);
		FYAddAccountRequestBean accountRequestBean = getAddResultBean(result);
		return accountRequestBean;
	}



	private FYAddAccountRequestBean getAddResultBean(String result) {
		// TODO Auto-generated method stub
		FYAddAccountRequestBean accountRequestBean = new FYAddAccountRequestBean();
		JSONObject jsonObject = JSONObject.parseObject(result);
		accountRequestBean = JSONObject.toJavaObject(jsonObject, FYAddAccountRequestBean.class);
		return accountRequestBean;
	}

	/*public String callEmpower(){

	}*/



	public String getAu(String ti) {
		return Md5Util.encode(AppInfo.APP_ID + AppInfo.APP_TOKEN + ti);
	}

	private String getFYUrl(String url) {
		return "http://" + FY_SERVER_IP + ":" + FY_SERVER_PORT + url;
	}
}

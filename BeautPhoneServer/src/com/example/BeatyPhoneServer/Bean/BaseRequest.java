package com.example.BeatyPhoneServer.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class BaseRequest {

	@TLVAttribute(tag=100, description="sessionId")
	public String sessionId = "";

	@TLVAttribute(tag=101, description="appPakageName")
	public String appPakageName = "";

	@TLVAttribute(tag=102, description="channelId")
	public String channelId = "";

	@TLVAttribute(tag=103, description= "appId")
	public String appId = "";

	@TLVAttribute(tag=104, description= "appToken")
	public String appToken = "";

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAppPakageName() {
		return appPakageName;
	}

	public void setAppPakageName(String appPakageName) {
		this.appPakageName = appPakageName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	@Override
	public String toString() {
		return "BaseRequest [sessionId=" + sessionId + ", appPakageName="
				+ appPakageName + ", channelId=" + channelId + ", appId="
				+ appId + ", appToken=" + appToken + "]";
	}


}

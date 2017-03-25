package com.example.BeatyPhoneServer.Bean;

public class FYCallLogPushResponse {
	private String resultCode = "0";
	private String resultMsg = "";
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	@Override
	public String toString() {
		return "FYCallLogPushResponse [resultCode=" + resultCode
				+ ", resultMsg=" + resultMsg + "]";
	}
	
}

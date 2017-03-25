package com.example.BeatyPhoneServer.Bean;

public class FYAddAccountRequestBean {
	private String resultMsg = "";
	private FYAddAccountResultBean result = null;
	private String resultCode = "";
	public FYAddAccountResultBean getResult() {
		return result;
	}
	public void setResult(FYAddAccountResultBean result) {
		this.result = result;
	}
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
		return "FYAddAccountRequestBean [result=" + result + ", resultCode="
				+ resultCode + ", resultMsg=" + resultMsg + "]";
	}
	
	
}

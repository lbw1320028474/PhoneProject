package com.example.BeatyPhoneServer.Bean;

public class FYEnpowerResponseBean {
	private String resultCode = "0";
	private String resultMsg = "";
	private FYEnpowerResult result = new FYEnpowerResult();
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
	public FYEnpowerResult getResult() {
		return result;
	}
	public void setResult(FYEnpowerResult result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "FYEnpowerResponseBean [resultCode=" + resultCode
				+ ", resultMsg=" + resultMsg + ", result=" + result + "]";
	}
	
	
}

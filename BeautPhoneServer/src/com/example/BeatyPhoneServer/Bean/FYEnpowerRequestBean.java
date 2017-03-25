package com.example.BeatyPhoneServer.Bean;

public class FYEnpowerRequestBean {
	private String action = "";
	private int callType = 1;
	private int showNumberType = 2;
	private String caller = "";
	private String callee = "";
	private String appExtraData = "";
	private String fyCallId = "";
	private String fyAccountId = "";
	private String appAccountId = "";
	private String appId = "";
	private String channelId = "";
	private int ifRecord = 2;
	private long ti;
	private String au = "";
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = Integer.parseInt(callType);
	}
	public int getShowNumberType() {
		return showNumberType;
	}
	public void setShowNumberType(String showNumberType) {
		this.showNumberType = Integer.parseInt(showNumberType);
	}
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public String getCallee() {
		return callee;
	}
	public void setCallee(String callee) {
		this.callee = callee;
	}
	public String getAppExtraData() {
		return appExtraData;
	}
	public void setAppExtraData(String appExtraData) {
		this.appExtraData = appExtraData;
	}
	public String getFyCallId() {
		return fyCallId;
	}
	public void setFyCallId(String fyCallId) {
		this.fyCallId = fyCallId;
	}
	public String getFyAccountId() {
		return fyAccountId;
	}
	public void setFyAccountId(String fyAccountId) {
		this.fyAccountId = fyAccountId;
	}
	public String getAppAccountId() {
		return appAccountId;
	}
	public void setAppAccountId(String appAccountId) {
		this.appAccountId = appAccountId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public int getIfRecord() {
		return ifRecord;
	}
	public void setIfRecord(String ifRecord) {
		this.ifRecord = Integer.parseInt(ifRecord);
	}
	public long getTi() {
		return ti;
	}
	public void setTi(String ti) {
		this.ti = Long.parseLong(ti);
	}
	public String getAu() {
		return au;
	}
	public void setAu(String au) {
		this.au = au;
	}
	@Override
	public String toString() {
		return "FYEnpowerBean [action=" + action + ", callType=" + callType
				+ ", showNumberType=" + showNumberType + ", caller=" + caller
				+ ", callee=" + callee + ", appExtraData=" + appExtraData
				+ ", fyCallId=" + fyCallId + ", fyAccountId=" + fyAccountId
				+ ", appAccountId=" + appAccountId + ", appId=" + appId
				+ ", channelId=" + channelId + ", ifRecord=" + ifRecord
				+ ", ti=" + ti + ", au=" + au + "]";
	}
}

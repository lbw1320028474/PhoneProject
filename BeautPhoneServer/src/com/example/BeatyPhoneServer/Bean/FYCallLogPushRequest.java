package com.example.BeatyPhoneServer.Bean;

public class FYCallLogPushRequest {
	private String action = "";
	private String appId = "";
	private String appCallId = "";
	private String fyCallId = "";
	private String appServerExtraData = "";
	private String callbackFirstStartTime = "";
	private String callbackFirstEndTime = "";
	private String callStartTime = "";
	private String callEndTime = "";
	private String callDuration = "";
	private String stopReason = "";
	private String trueShowNumberType = "";
	private String trueIfRecord = "";
	private String time = "";
	private String au = "";
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppCallId() {
		return appCallId;
	}
	public void setAppCallId(String appCallId) {
		this.appCallId = appCallId;
	}
	public String getFyCallId() {
		return fyCallId;
	}
	public void setFyCallId(String fyCallId) {
		this.fyCallId = fyCallId;
	}
	public String getAppServerExtraData() {
		return appServerExtraData;
	}
	public void setAppServerExtraData(String appServerExtraData) {
		this.appServerExtraData = appServerExtraData;
	}
	public String getCallbackFirstStartTime() {
		return callbackFirstStartTime;
	}
	public void setCallbackFirstStartTime(String callbackFirstStartTime) {
		this.callbackFirstStartTime = callbackFirstStartTime;
	}
	public String getCallbackFirstEndTime() {
		return callbackFirstEndTime;
	}
	public void setCallbackFirstEndTime(String callbackFirstEndTime) {
		this.callbackFirstEndTime = callbackFirstEndTime;
	}
	public String getCallStartTime() {
		return callStartTime;
	}
	public void setCallStartTime(String callStartTime) {
		this.callStartTime = callStartTime;
	}
	public String getCallEndTime() {
		return callEndTime;
	}
	public void setCallEndTime(String callEndTime) {
		this.callEndTime = callEndTime;
	}
	
	public String getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}
	public String getStopReason() {
		return stopReason;
	}
	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}
	public String getTrueShowNumberType() {
		return trueShowNumberType;
	}
	public void setTrueShowNumberType(String trueShowNumberType) {
		this.trueShowNumberType = trueShowNumberType;
	}
	public String getTrueIfRecord() {
		return trueIfRecord;
	}
	public void setTrueIfRecord(String trueIfRecord) {
		this.trueIfRecord = trueIfRecord;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAu() {
		return au;
	}
	public void setAu(String au) {
		this.au = au;
	}
	@Override
	public String toString() {
		return "FYCallLogPushRequest [action=" + action + ", appId=" + appId
				+ ", appCallId=" + appCallId + ", fyCallId=" + fyCallId
				+ ", appServerExtraData=" + appServerExtraData
				+ ", callbackFirstStartTime=" + callbackFirstStartTime
				+ ", callbackFirstEndTime=" + callbackFirstEndTime
				+ ", callStartTime=" + callStartTime + ", callEndTime="
				+ callEndTime + ", callDuration=" + callDuration
				+ ", stopReason=" + stopReason + ", trueShowNumberType="
				+ trueShowNumberType + ", trueIfRecord=" + trueIfRecord
				+ ", time=" + time + ", au=" + au + "]";
	}

}

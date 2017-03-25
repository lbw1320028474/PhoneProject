package com.example.BeatyPhoneServer.Bean;

public class FYEnpowerResult {
	private String appCallId = "";
	private String appServerExtraData = "";
	private String fyCallId = "";
	private int showNumberType = 2;
	private int maxCallMinute = 10;
	private int ifRecord = 2;
	public String getAppCallId() {
		return appCallId;
	}
	public void setAppCallId(String appCallId) {
		this.appCallId = appCallId;
	}
	public String getAppServerExtraData() {
		return appServerExtraData;
	}
	public void setAppServerExtraData(String appServerExtraData) {
		this.appServerExtraData = appServerExtraData;
	}
	public String getFyCallId() {
		return fyCallId;
	}
	public void setFyCallId(String fyCallId) {
		this.fyCallId = fyCallId;
	}
	public int getShowNumberType() {
		return showNumberType;
	}
	public void setShowNumberType(int showNumberType) {
		this.showNumberType = showNumberType;
	}
	public int getMaxCallMinute() {
		return maxCallMinute;
	}
	public void setMaxCallMinute(int maxCallMinute) {
		this.maxCallMinute = maxCallMinute;
	}
	public int getIfRecord() {
		return ifRecord;
	}
	public void setIfRecord(int ifRecord) {
		this.ifRecord = ifRecord;
	}
	@Override
	public String toString() {
		return "FYEnpowerResult [appCallId=" + appCallId
				+ ", appServerExtraData=" + appServerExtraData + ", fyCallId="
				+ fyCallId + ", showNumberType=" + showNumberType
				+ ", maxCallMinute=" + maxCallMinute + ", ifRecord=" + ifRecord
				+ "]";
	}

}

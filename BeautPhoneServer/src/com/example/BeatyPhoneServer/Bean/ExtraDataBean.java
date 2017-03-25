package com.example.BeatyPhoneServer.Bean;

public class ExtraDataBean {
	private String fromAppAccount = "";
	private String toAppAccount = "";
	private String fromFyAccount = "";
	private String toFyAccount = "";
	private String extraData = "";
	public String getFromAppAccount() {
		return fromAppAccount;
	}
	public void setFromAppAccount(String fromAppAccount) {
		this.fromAppAccount = fromAppAccount;
	}
	public String getToAppAccount() {
		return toAppAccount;
	}
	public void setToAppAccount(String toAppAccount) {
		this.toAppAccount = toAppAccount;
	}
	public String getFromFyAccount() {
		return fromFyAccount;
	}
	public void setFromFyAccount(String fromFyAccount) {
		this.fromFyAccount = fromFyAccount;
	}
	public String getToFyAccount() {
		return toFyAccount;
	}
	public void setToFyAccount(String toFyAccount) {
		this.toFyAccount = toFyAccount;
	}
	public String getExtraData() {
		return extraData;
	}
	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}
	@Override
	public String toString() {
		return "ExtraDataBean [fromAppAccount=" + fromAppAccount
				+ ", toAppAccount=" + toAppAccount + ", fromFyAccount="
				+ fromFyAccount + ", toFyAccount=" + toFyAccount
				+ ", extraData=" + extraData + "]";
	}
	
	
}

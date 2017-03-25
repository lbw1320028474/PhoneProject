package com.example.BeatyPhoneServer.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class RegistResponseBean extends BaseResponse {
	@TLVAttribute(tag=2001, description= "userId")
	public String userId = "";

	@TLVAttribute(tag=2002, description= "userAccount")
	public String userAccount = "";

	@TLVAttribute(tag=2003, description= "userNumber")
	public String userNumber = "";

	@TLVAttribute(tag=2004, description= "fyAccount")
	public String fyAccount = "";

	@TLVAttribute(tag=2005, description= "fyPassword")
	public String fyPassword = "";

	@TLVAttribute(tag=2016, description= "registResult")
	public String registResult = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getFyAccount() {
		return fyAccount;
	}

	public void setFyAccount(String fyAccount) {
		this.fyAccount = fyAccount;
	}

	public String getFyPassword() {
		return fyPassword;
	}

	public void setFyPassword(String fyPassword) {
		this.fyPassword = fyPassword;
	}

	public String getRegistResult() {
		return registResult;
	}

	public void setRegistResult(String registResult) {
		this.registResult = registResult;
	}

	@Override
	public String toString() {
		return "RegistResponseBean [userId=" + userId + ", userAccount=" + userAccount + ", userNumber=" + userNumber
				+ ", fyAccount=" + fyAccount + ", fyPassword=" + fyPassword + ", registResult=" + registResult + "]";
	}
	
	
}

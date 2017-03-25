package com.example.BeatyPhoneServer.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class RegistRequestBean extends BaseRequest {

	@TLVAttribute(tag=1001, description = "userAccount")
	private String userAccount = "";

	@TLVAttribute(tag=1002, description = "regist_password")
	private String userPassword = "";

	@TLVAttribute(tag=1003, description= "number")
	public String userNumber = "";

	@TLVAttribute(tag=1004, description= "identifyCode")
	public String identifyCode = "";

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	@Override
	public String toString() {
		return "RegistRequestBean [userAccount=" + userAccount
				+ ", userPassword=" + userPassword + ", userNumber="
				+ userNumber + ", identifyCode=" + identifyCode + "]";
	}



}

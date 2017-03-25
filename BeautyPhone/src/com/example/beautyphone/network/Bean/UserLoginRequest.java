package com.example.beautyphone.network.Bean;


import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class UserLoginRequest extends BaseRequest{

	@TLVAttribute(tag=1101, description = "userName")
	public String userName = "";

	@TLVAttribute(tag=1102, description = "userPassowrd")
	public String userPassowrd = "";

	@TLVAttribute(tag=1103, description= "identifyCode")
	public String identifyCode = "";

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassowrd() {
		return userPassowrd;
	}

	public void setUserPassowrd(String userPassowrd) {
		this.userPassowrd = userPassowrd;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	@Override
	public String toString() {
		return "UserLoginRequest [userName=" + userName + ", userPassowrd=" + userPassowrd + ", identifyCode="
				+ identifyCode + "]";
	}

	
}

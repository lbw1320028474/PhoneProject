package com.example.beautyphone.network.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class UserLoginResponse extends BaseResponse{

	@TLVAttribute(tag=2101, description = "userAccount")
	public String userAccount = "";

	@TLVAttribute(tag=2102, description= "fyAccount")
	public String fyAccount = "";

	@TLVAttribute(tag=2103, description= "fyPassword")
	public String fyPassword = "";

	@TLVAttribute(tag=2104, description= "userState")
	public String userState = "";

	@TLVAttribute(tag=2105, description= "creatDate")
	public String creatDate = "";

	@TLVAttribute(tag=2106, description= "userName")
	public String userName = "";

	@TLVAttribute(tag=2107, description= "userNikeName")
	public String userNikeName = "";

	@TLVAttribute(tag=2108, description= "userNumber")
	public String userNumber = "";

	@TLVAttribute(tag=2109, description= "userVipState")
	public String userVipState = "";

	@TLVAttribute(tag=2110, description= "maxPhoneTime")
	public String maxPhoneTime = "";

	@TLVAttribute(tag=2111, description= "loginState")
	public int loginState = 0;

	@TLVAttribute(tag=2112, description= "loginMessage")
	public String loginMessage = "";
	
	@TLVAttribute(tag=2113, description= "loginMessage")
	public String balanceMoney = "";
	
	

	public String getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(String balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNikeName() {
		return userNikeName;
	}

	public void setUserNikeName(String userNikeName) {
		this.userNikeName = userNikeName;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserVipState() {
		return userVipState;
	}

	public void setUserVipState(String userVipState) {
		this.userVipState = userVipState;
	}

	public String getMaxPhoneTime() {
		return maxPhoneTime;
	}

	public void setMaxPhoneTime(String maxPhoneTime) {
		this.maxPhoneTime = maxPhoneTime;
	}

	public int getLoginState() {
		return loginState;
	}

	public void setLoginState(int loginState) {
		this.loginState = loginState;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	@Override
	public String toString() {
		return "UserLoginResponse [userAccount=" + userAccount + ", fyAccount=" + fyAccount + ", fyPassword="
				+ fyPassword + ", userState=" + userState + ", creatDate=" + creatDate + ", userName=" + userName
				+ ", userNikeName=" + userNikeName + ", userNumber=" + userNumber + ", userVipState=" + userVipState
				+ ", maxPhoneTime=" + maxPhoneTime + ", loginState=" + loginState + ", loginMessage=" + loginMessage
				+ ", balanceMoney=" + balanceMoney + "]";
	}


}

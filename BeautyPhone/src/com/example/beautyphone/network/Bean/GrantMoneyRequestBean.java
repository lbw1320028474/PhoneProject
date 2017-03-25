package com.example.beautyphone.network.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class GrantMoneyRequestBean extends BaseRequest {
	@TLVAttribute(tag=3301, description= "userAccount")
	private String videoTitle = "";
	@TLVAttribute(tag=3302, description= "userAccount")
	private String videoId = "";
	@TLVAttribute(tag=3303, description= "userAccount")
	private String grantUser = "";
	@TLVAttribute(tag=3304, description= "userAccount")
	private String grantUserAccount = "";
	@TLVAttribute(tag=3305, description= "userAccount")
	private String grantUserMoney = "";
	@TLVAttribute(tag=3306, description= "userAccount")
	private String grantTime = "";
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getGrantUser() {
		return grantUser;
	}
	public void setGrantUser(String grantUser) {
		this.grantUser = grantUser;
	}
	public String getGrantUserAccount() {
		return grantUserAccount;
	}
	public void setGrantUserAccount(String grantUserAccount) {
		this.grantUserAccount = grantUserAccount;
	}
	public String getGrantUserMoney() {
		return grantUserMoney;
	}
	public void setGrantUserMoney(String grantUserMoney) {
		this.grantUserMoney = grantUserMoney;
	}
	public String getGrantTime() {
		return grantTime;
	}
	public void setGrantTime(String grantTime) {
		this.grantTime = grantTime;
	}
	@Override
	public String toString() {
		return "GrantMoneyRequestBean [videoTitle=" + videoTitle + ", videoId=" + videoId + ", grantUser=" + grantUser
				+ ", grantUserAccount=" + grantUserAccount + ", grantUserMoney=" + grantUserMoney + ", grantTime="
				+ grantTime + "]";
	}
	
}

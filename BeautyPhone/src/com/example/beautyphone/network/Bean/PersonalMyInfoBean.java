package com.example.beautyphone.network.Bean;

import com.example.beautyphone.news.bean.PersonalRecycleBaseBean;

public class PersonalMyInfoBean extends PersonalRecycleBaseBean {
	private String converImageUrl = "";
	private String userAccount = "";
	private String vipCount = "";
	private String black_money = "";
	private String black_calltime = "";
	public String getConverImageUrl() {
		return converImageUrl;
	}
	public void setConverImageUrl(String converImageUrl) {
		this.converImageUrl = converImageUrl;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getVipCount() {
		return vipCount;
	}
	public void setVipCount(String vipCount) {
		this.vipCount = vipCount;
	}
	public String getBlack_money() {
		return black_money;
	}
	public void setBlack_money(String black_money) {
		this.black_money = black_money;
	}
	public String getBlack_calltime() {
		return black_calltime;
	}
	public void setBlack_calltime(String black_calltime) {
		this.black_calltime = black_calltime;
	}
	@Override
	public String toString() {
		return "PersonalMyInfoBean [converImageUrl=" + converImageUrl + ", userAccount=" + userAccount + ", vipCount="
				+ vipCount + ", black_money=" + black_money + ", black_calltime=" + black_calltime + "]";
	}

}

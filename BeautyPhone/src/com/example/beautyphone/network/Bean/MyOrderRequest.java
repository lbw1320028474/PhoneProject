package com.example.beautyphone.network.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class MyOrderRequest extends BaseRequest {
	@TLVAttribute(tag=3001, description= "userAccount")
	private String userAccount = "";

	@TLVAttribute(tag=3002, description= "payOrderId")
	private String payOrderId = "";

	@TLVAttribute(tag=3003, description= "payOrderId")
	private String payMoney = "";

	@TLVAttribute(tag=3004, description= "payOrderId")
	private String truePayMoney = "";

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public String getTruePayMoney() {
		return truePayMoney;
	}

	public void setTruePayMoney(String truePayMoney) {
		this.truePayMoney = truePayMoney;
	}

	@Override
	public String toString() {
		return "MyOrderRequest [userAccount=" + userAccount + ", payOrderId="
				+ payOrderId + ", payMoney=" + payMoney + ", truePayMoney="
				+ truePayMoney + "]";
	}


}

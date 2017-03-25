package com.example.beautyphone.network.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class MyOrderResponse extends BaseResponse {
	@TLVAttribute(tag=3101, description= "userAccount")
	private String getOrderState = "";

	public String getGetOrderState() {
		return getOrderState;
	}

	public void setGetOrderState(String getOrderState) {
		this.getOrderState = getOrderState;
	}

	@Override
	public String toString() {
		return "MyOrderResponse [getOrderState=" + getOrderState + "]";
	}


}

package com.example.beautyphone.network.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class BaseResponse {

	@TLVAttribute(tag=200, description="resultCode")
	private String resultCode = "200";

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@Override
	public String toString() {
		return "BaseResponse [resultCode=" + resultCode + "]";
	}
	
}

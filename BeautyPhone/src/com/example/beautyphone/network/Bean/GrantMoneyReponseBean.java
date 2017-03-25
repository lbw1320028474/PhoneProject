package com.example.beautyphone.network.Bean;

import com.skymobi.pay.tlv.annotation.TLVAttribute;

public class GrantMoneyReponseBean extends BaseResponse {
	@TLVAttribute(tag=3311, description= "userAccount")
	private String grantState = "";
	@TLVAttribute(tag=3312, description= "userAccount")
	private String blackMoney = "";
	public String getGrantState() {
		return grantState;
	}
	public void setGrantState(String grantState) {
		this.grantState = grantState;
	}
	public String getBlackMoney() {
		return blackMoney;
	}
	public void setBlackMoney(String blackMoney) {
		this.blackMoney = blackMoney;
	}
	@Override
	public String toString() {
		return "GrantMoneyReponseBean [grantState=" + grantState + ", blackMoney=" + blackMoney + "]";
	}

}

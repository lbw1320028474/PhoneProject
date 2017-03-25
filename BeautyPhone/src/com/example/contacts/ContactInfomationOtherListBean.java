package com.example.contacts;

import java.util.ArrayList;

import com.example.contacts.infomation.InfomationBaseData;

public class ContactInfomationOtherListBean extends InfomationBaseData {
	private ArrayList<OhterInfomationBean> otherInfomationList = null;

	public ArrayList<OhterInfomationBean> getOtherInfomationList() {
		return otherInfomationList;
	}

	public void setOtherInfomationList(ArrayList<OhterInfomationBean> otherInfomationList) {
		this.otherInfomationList = otherInfomationList;
	}
	
}

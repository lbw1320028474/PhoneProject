package com.example.contacts;

import java.util.ArrayList;

import com.example.contacts.infomation.InfomationBaseData;

public class ContactInfomationNumberListBean extends InfomationBaseData {
	private ArrayList<NumberInfomation> numberList = null;

	public ArrayList<NumberInfomation> getNumberList() {
		return numberList;
	}

	public void setNumberList(ArrayList<NumberInfomation> numberList) {
		this.numberList = numberList;
	}
	
	
}

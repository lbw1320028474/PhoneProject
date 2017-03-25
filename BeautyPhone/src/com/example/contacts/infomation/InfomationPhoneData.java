package com.example.contacts.infomation;

import java.util.ArrayList;

import com.example.contacts.NumberInfomation;

public class InfomationPhoneData extends InfomationBaseData {
	private ArrayList<NumberInfomation> contactsPhoneNumberList = null;

	public ArrayList<NumberInfomation> getContactsPhoneNumberList() {
		return contactsPhoneNumberList;
	}

	public void setContactsPhoneNumberList(ArrayList<NumberInfomation> contactsPhoneNumberList) {
		this.contactsPhoneNumberList = contactsPhoneNumberList;
	}
}

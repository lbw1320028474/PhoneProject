package com.example.contacts;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class CallSearchResultBean extends CallLogBean{
	private int contact_Id = -1;		//
	private String contact_name = "";		//
	private ArrayList<String> numberList = null;		//
	private String contactCover_Id = "";	//
	private Bitmap contactCover_Bitmap = null;		//
	private String serchNumber = "";
	
	
	public String getSerchNumber() {
		return serchNumber;
	}
	public void setSerchNumber(String serchNumber) {
		this.serchNumber = serchNumber;
	}
	public int getContact_Id() {
		return contact_Id;
	}
	public void setContact_Id(int contact_Id) {
		this.contact_Id = contact_Id;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public ArrayList<String> getNumberList() {
		return numberList;
	}
	public void setNumberList(ArrayList<String> numberList) {
		this.numberList = numberList;
	}
	public String getContactCover_Id() {
		return contactCover_Id;
	}
	public void setContactCover_Id(String contactCover_Id) {
		this.contactCover_Id = contactCover_Id;
	}
	public Bitmap getContactCover_Bitmap() {
		return contactCover_Bitmap;
	}
	public void setContactCover_Bitmap(Bitmap contactCover_Bitmap) {
		this.contactCover_Bitmap = contactCover_Bitmap;
	}
	
}

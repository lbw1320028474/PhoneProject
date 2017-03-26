package com.example.contacts;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * 联系人数据类
 * @author Administrator
 *
 */
public class ContactPeopleBean{
	private int converColor = ConverBitmapSourse.converBitmapColor[0];
	private String isFirstItemShowChar = "";
	private String namePinYFirstChar = "";
	private int itemType = 2;
	private ArrayList<ContactPeopleBean> sterredList = null;
	private String namePinyin = "";
	private int contactId = -1;		//
	private String name = "";		//
	private ArrayList<String> numberList = null;		//
	private String contactCoverId = "";	//
	private Bitmap contactCoverBitmap = null;		//
	private int starred = -1;		//
	public int getConverColor() {
		return converColor;
	}
	public void setConverColor(int converColor) {
		this.converColor = converColor;
	}
	public String getIsFirstItemShowChar() {
		return isFirstItemShowChar;
	}
	public void setIsFirstItemShowChar(String isFirstItemShowChar) {
		this.isFirstItemShowChar = isFirstItemShowChar;
	}
	public String getNamePinYFirstChar() {
		return namePinYFirstChar;
	}
	public void setNamePinYFirstChar(String namePinYFirstChar) {
		this.namePinYFirstChar = namePinYFirstChar;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public ArrayList<ContactPeopleBean> getSterredList() {
		return sterredList;
	}
	public void setSterredList(ArrayList<ContactPeopleBean> sterredList) {
		this.sterredList = sterredList;
	}
	public String getNamePinyin() {
		return namePinyin;
	}
	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getNumberList() {
		return numberList;
	}
	public void setNumberList(ArrayList<String> numberList) {
		this.numberList = numberList;
	}
	public String getContactCoverId() {
		return contactCoverId;
	}
	public void setContactCoverId(String contactCoverId) {
		this.contactCoverId = contactCoverId;
	}
	public Bitmap getContactCoverBitmap() {
		return contactCoverBitmap;
	}
	public void setContactCoverBitmap(Bitmap contactCoverBitmap) {
		this.contactCoverBitmap = contactCoverBitmap;
	}
	public int getStarred() {
		return starred;
	}
	public void setStarred(int starred) {
		this.starred = starred;
	}
	@Override
	public String toString() {
		return "ContactPeopleBean [converColor=" + converColor + ", isFirstItemShowChar=" + isFirstItemShowChar
				+ ", namePinYFirstChar=" + namePinYFirstChar + ", itemType=" + itemType + ", sterredList=" + sterredList
				+ ", namePinyin=" + namePinyin + ", contactId=" + contactId + ", name=" + name + ", numberList="
				+ numberList + ", contactCoverId=" + contactCoverId + ", contactCoverBitmap=" + contactCoverBitmap
				+ ", starred=" + starred + "]";
	}



}

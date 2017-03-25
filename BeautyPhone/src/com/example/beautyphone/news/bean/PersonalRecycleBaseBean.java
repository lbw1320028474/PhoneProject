package com.example.beautyphone.news.bean;

public class PersonalRecycleBaseBean {
	private int itemType = 0;

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		return "PersonalRecycleBaseBean [itemType=" + itemType + "]";
	}

}

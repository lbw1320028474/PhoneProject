package com.example.contacts;

import java.io.Serializable;

public class CardLocationBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String number = "";
	private String province = "";
	private String city = "";
	private String simType = "";
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSimType() {
		return simType;
	}
	public void setSimType(String simType) {
		this.simType = simType;
	}
	@Override
	public String toString() {
		return "CardLocationBean [province=" + province + ", city=" + city + ", simType=" + simType + "]";
	}


}

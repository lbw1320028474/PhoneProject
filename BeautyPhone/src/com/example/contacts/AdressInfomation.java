package com.example.contacts;

public class AdressInfomation {
	private String adress = "";
	private String adressType = "";
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getAdressType() {
		return adressType;
	}
	public void setAdressType(String adressType) {
		this.adressType = adressType;
	}
	@Override
	public String toString() {
		return "AdressInfomation [adress=" + adress + ", adressType=" + adressType + "]";
	}
	
}

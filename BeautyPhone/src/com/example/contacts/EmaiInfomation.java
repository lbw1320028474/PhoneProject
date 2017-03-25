package com.example.contacts;

public class EmaiInfomation {
	private String emailAdress = "";
	private String emailType = "";
	public String getEmailAdress() {
		return emailAdress;
	}
	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	@Override
	public String toString() {
		return "EmaiInfomation [emailAdress=" + emailAdress + ", emailType=" + emailType + "]";
	}
	
}

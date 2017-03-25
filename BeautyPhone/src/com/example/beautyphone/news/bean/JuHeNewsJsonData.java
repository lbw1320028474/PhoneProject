package com.example.beautyphone.news.bean;

public class JuHeNewsJsonData {
	private String newType = "";
	private boolean isLoadOk = true;
	private String reason = "";
	private JuHeNewsDataBean result= null;

	public JuHeNewsJsonData(){
		result = new JuHeNewsDataBean();
	}

	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}

	public boolean isLoadOk() {
		return isLoadOk;
	}

	public void setLoadOk(boolean isLoadOk) {
		this.isLoadOk = isLoadOk;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public JuHeNewsDataBean getResult() {
		return result;
	}

	public void setResult(JuHeNewsDataBean result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "JuHeNewsJsonData [newType=" + newType + ", isLoadOk=" + isLoadOk + ", reason=" + reason + ", result="
				+ result + "]";
	}



}

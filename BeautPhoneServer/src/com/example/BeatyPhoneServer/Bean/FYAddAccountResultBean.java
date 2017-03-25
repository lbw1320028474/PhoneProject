package com.example.BeatyPhoneServer.Bean;

public class FYAddAccountResultBean {
	private String fyAccountPwd = "";
	private int status = 1;
	private String fyAccountId = "";
	private String addDate = "";
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getFyAccountId() {
		return fyAccountId;
	}
	public void setFyAccountId(String fyAccountId) {
		this.fyAccountId = fyAccountId;
	}
	public String getFyAccountPwd() {
		return fyAccountPwd;
	}
	public void setFyAccountPwd(String fyAccountPwd) {
		this.fyAccountPwd = fyAccountPwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FYAddAccountResultBean [addDate=" + addDate + ", fyAccountId="
				+ fyAccountId + ", fyAccountPwd=" + fyAccountPwd + ", status="
				+ status + "]";
	}
	
	
}

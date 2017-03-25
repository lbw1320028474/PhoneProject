package com.example.contacts;

public class OhterInfomationBean {
	private String title = "";
	private String data = "";
	private String descript = "";
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	@Override
	public String toString() {
		return "OhterInfomationBean [title=" + title + ", data=" + data + ", descript=" + descript + "]";
	}
	
}

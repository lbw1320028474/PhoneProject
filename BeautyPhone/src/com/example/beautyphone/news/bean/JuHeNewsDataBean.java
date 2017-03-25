package com.example.beautyphone.news.bean;

import java.util.ArrayList;

public class JuHeNewsDataBean {
	private String stat = "";
	private ArrayList<JuHeNewsJsonItemBean> data = null;
	public JuHeNewsDataBean(){
		data = new ArrayList<JuHeNewsJsonItemBean>();
	}
	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public ArrayList<JuHeNewsJsonItemBean> getData() {
		return data;
	}

	public void setData(ArrayList<JuHeNewsJsonItemBean> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JuHeNewsDataBean [stat=" + stat + ", data=" + data + "]";
	}

}

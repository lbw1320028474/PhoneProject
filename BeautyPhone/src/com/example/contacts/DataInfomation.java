package com.example.contacts;

public class DataInfomation {
	private String data = "";
	private String dataType = "";
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	@Override
	public String toString() {
		return "DataInfomation [data=" + data + ", dataType=" + dataType + "]";
	}
	
}

package com.example.contacts;

import com.example.util.DataConverterUtil.DataBean;

public class ContactInfomationCalllogItemBean extends ContactInfomationCllllogBaseBean{
	private int callogId = -1;		//通话记录ID
	private String name = "";		//通话记录联系人名字
	private String number = "";		//通话记录联系人电话
	private String callLogdate = "";	//通话记录
	private int duration = 0;		//通话记录持续时间
	private String numberAdress = "";		//通话记录地址
	private int logType = -1;		//通话记录类型
	private int numberType = -1;		//号码类型
	private DataBean dataBean = null;
	
	
	public DataBean getDataBean() {
		return dataBean;
	}
	public void setDataBean(DataBean dataBean) {
		this.dataBean = dataBean;
	}
	public int getCallogId() {
		return callogId;
	}
	public void setCallogId(int callogId) {
		this.callogId = callogId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCallLogdate() {
		return callLogdate;
	}
	public void setCallLogdate(String callLogdate) {
		this.callLogdate = callLogdate;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getNumberAdress() {
		return numberAdress;
	}
	public void setNumberAdress(String numberAdress) {
		this.numberAdress = numberAdress;
	}
	public int getLogType() {
		return logType;
	}
	public void setLogType(int logType) {
		this.logType = logType;
	}
	public int getNumberType() {
		return numberType;
	}
	public void setNumberType(int numberType) {
		this.numberType = numberType;
	}
}

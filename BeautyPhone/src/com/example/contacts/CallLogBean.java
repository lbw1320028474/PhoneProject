package com.example.contacts;

import com.example.util.DataConverterUtil.DataBean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ͨ����¼��������
 * @author Administrator
 * 
 */
public class CallLogBean{
	private boolean isSelected = false;
	private boolean isEdit = false;
	private int itemType = 0;		//Item����
	private int callogId = -1;		//ͨ����¼ID
	private String name = "";		//ͨ����¼��ϵ������
	private String number = "";		//ͨ����¼��ϵ�˵绰
	private String callLogdate = "";	//ͨ����¼
	private int duration = 0;		//ͨ����¼����ʱ��
	private String numberAdress = "";		//ͨ����¼��ַ
	private int logType = -1;		//ͨ����¼����
	private int numberType = -1;		//��������
	private DataBean dataBean = null;
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public boolean isEdit() {
		return isEdit;
	}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
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
	public DataBean getDataBean() {
		return dataBean;
	}
	public void setDataBean(DataBean dataBean) {
		this.dataBean = dataBean;
	}
	@Override
	public String toString() {
		return "CallLogBean [isSelected=" + isSelected + ", isEdit=" + isEdit + ", itemType=" + itemType + ", callogId="
				+ callogId + ", name=" + name + ", number=" + number + ", callLogdate=" + callLogdate + ", duration="
				+ duration + ", numberAdress=" + numberAdress + ", logType=" + logType + ", numberType=" + numberType
				+ ", dataBean=" + dataBean + "]";
	}

}

package com.example.contacts;

import java.util.ArrayList;

public class ContactInfomationBean extends ContactInfomationBaseBean{
	private String contact_id = "";		//联系人ID
	private String contact_cover_id = "";	//联系人头像ID
	private String name = "";		//姓名
	private String company = "";		//公司
	private String job = "";		//职位		
	private ArrayList<NumberInfomation> numberList = null;	//电话列表
	private String group = "";		//分组
	private ArrayList<DataInfomation> dataList = null;		//日期列表
	private ArrayList<EmaiInfomation> emailList = null;		//邮箱列表
	private ArrayList<AdressInfomation> adressList = null;	//地址列表
	private String note = "";		//备注
	private String nikeName = "";		//昵称
	private String webSite = "";		//个人主页
	private int itemType = 0;
	
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getContact_id() {
		return contact_id;
	}
	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}
	public String getContact_cover_id() {
		return contact_cover_id;
	}
	public void setContact_cover_id(String contact_cover_id) {
		this.contact_cover_id = contact_cover_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public ArrayList<NumberInfomation> getNumberList() {
		return numberList;
	}
	public void setNumberList(ArrayList<NumberInfomation> numberList) {
		this.numberList = numberList;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public ArrayList<DataInfomation> getDataList() {
		return dataList;
	}
	public void setDataList(ArrayList<DataInfomation> dataList) {
		this.dataList = dataList;
	}
	public ArrayList<EmaiInfomation> getEmailList() {
		return emailList;
	}
	public void setEmailList(ArrayList<EmaiInfomation> emailList) {
		this.emailList = emailList;
	}
	public ArrayList<AdressInfomation> getAdressList() {
		return adressList;
	}
	public void setAdressList(ArrayList<AdressInfomation> adressList) {
		this.adressList = adressList;
	}
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	@Override
	public String toString() {
		return "ContactInfomationBean [contact_id=" + contact_id + ", contact_cover_id=" + contact_cover_id + ", name="
				+ name + ", company=" + company + ", job=" + job + ", numberList=" + numberList + ", group=" + group
				+ ", dataList=" + dataList + ", emailList=" + emailList + ", adressList=" + adressList + ", note="
				+ note + ", nikeName=" + nikeName + ", webSite=" + webSite + ", itemType=" + itemType + "]";
	}

}

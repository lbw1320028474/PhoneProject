package com.example.contacts;

import java.util.ArrayList;

public class ContactInfomationBean extends ContactInfomationBaseBean{
	private String contact_id = "";		//��ϵ��ID
	private String contact_cover_id = "";	//��ϵ��ͷ��ID
	private String name = "";		//����
	private String company = "";		//��˾
	private String job = "";		//ְλ		
	private ArrayList<NumberInfomation> numberList = null;	//�绰�б�
	private String group = "";		//����
	private ArrayList<DataInfomation> dataList = null;		//�����б�
	private ArrayList<EmaiInfomation> emailList = null;		//�����б�
	private ArrayList<AdressInfomation> adressList = null;	//��ַ�б�
	private String note = "";		//��ע
	private String nikeName = "";		//�ǳ�
	private String webSite = "";		//������ҳ
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

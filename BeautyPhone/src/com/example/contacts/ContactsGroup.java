package com.example.contacts;

public class ContactsGroup{
	private String group_id = "";
	private String group_name = "";
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	@Override
	public String toString() {
		return "ContactsGroup [group_id=" + group_id + ", group_name=" + group_name + "]";
	}
	
}

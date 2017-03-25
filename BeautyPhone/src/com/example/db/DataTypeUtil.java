package com.example.db;

import com.example.contacts.ContactsGroup;

public class DataTypeUtil {
	public static String getNumberDescript(String type){
		String descript = "";
		switch (type) {
		case "0":
			descript = "�ֻ�";
			break;
		case "1":
			descript = "סլ";
			break;
		case "2":
			descript = "��λ";
			break;
		case "3":
			descript = "�ܻ�";
			break;
		case "4":
			descript = "��λ����";
			break;
		case "5":
			descript = "סլ����";
			break;
		case "6":
			descript = "Ѱ����";
			break;
		default:
			descript = "����";
			break;
		}
		return descript;
	}

	public static String getGroupName(String id){
		if(ContactsUtil.groupList != null && ContactsUtil.groupList.size() > 0){
			for (ContactsGroup g:ContactsUtil.groupList){
				if(id.equals(g.getGroup_id())){
					return g.getGroup_name();
				}
			}
		}
		return "δ֪����";

	}
	public static String getGroupId(String name){
		if(ContactsUtil.groupList != null && ContactsUtil.groupList.size() > 0){
			for (ContactsGroup g:ContactsUtil.groupList){
				if(name.equals(g.getGroup_name())){
					return g.getGroup_id();
				}
			}
		}
		return "0";
	}

	public static String getDataDescript(String type){
		String descript = "";
		switch (type) {
		case "0":
			descript = "����";
			break;
		case "1":
			descript = "�������";
			break;
		default:
			descript = "����";
			break;
		}
		return descript;
	}

	public static String getAdressDescript(String type){
		String descript = "";
		switch (type) {
		case "0":
			descript = "��λ";
			break;
		case "1":
			descript = "סլ";
			break;
		default:
			descript = "����";
			break;
		}
		return descript;
	}

	public static String getEmailDescript(String type){
		String descript = "";
		switch (type) {
		case "0":
			descript = "QQ";
			break;
		default:
			descript = "����";
			break;
		}
		return descript;
	}
}

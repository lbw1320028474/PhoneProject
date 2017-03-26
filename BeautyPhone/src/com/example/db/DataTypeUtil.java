package com.example.db;

import com.example.contacts.ContactsGroup;

public class DataTypeUtil {
	public static String getNumberDescript(String type){
		String descript = "";
		switch (type) {
		case "0":
			descript = "手机";
			break;
		case "1":
			descript = "住宅";
			break;
		case "2":
			descript = "单位";
			break;
		case "3":
			descript = "总机";
			break;
		case "4":
			descript = "单位传真";
			break;
		case "5":
			descript = "住宅传真";
			break;
		case "6":
			descript = "寻呼机";
			break;
		default:
			descript = "其它";
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
		return "未知分组";

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
			descript = "生日";
			break;
		case "1":
			descript = "周年纪念";
			break;
		default:
			descript = "其它";
			break;
		}
		return descript;
	}

	public static String getAdressDescript(String type){
		String descript = "";
		switch (type) {
		case "0":
			descript = "单位";
			break;
		case "1":
			descript = "住宅";
			break;
		default:
			descript = "其它";
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
			descript = "其它";
			break;
		}
		return descript;
	}
}

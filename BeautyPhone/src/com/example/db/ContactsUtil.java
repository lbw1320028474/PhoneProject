package com.example.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.beautyphone.StaticObject;
import com.example.contacts.AdressInfomation;
import com.example.contacts.CallLogBean;
import com.example.contacts.ContactInfomationBean;
import com.example.contacts.ContactInfomationCalllogItemBean;
import com.example.contacts.ContactPeopleBean;
import com.example.contacts.ContactsGroup;
import com.example.contacts.ConverBitmapSourse;
import com.example.contacts.DataInfomation;
import com.example.contacts.EmaiInfomation;
import com.example.contacts.MimetypeString;
import com.example.contacts.MyAsyncQueryHandler;
import com.example.contacts.MyAsyncQueryHandler.handlerQueryCallBack;
import com.example.contacts.NewCallLogBean;
import com.example.contacts.NumberInfomation;
import com.example.util.BitmapUtil;
import com.example.util.Cn2Spell;
import com.example.util.DataConverterUtil;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactsUtil {
	private static Context context = null;
	public static ContactInfomationBean infomationBean = null;
	public static ArrayList<ContactsGroup> groupList = null;
	public static ArrayList<ContactInfomationCalllogItemBean> subcalllogList = null;
	public interface contactsCallBack{
		public void allContactsCuror(ArrayList<ContactPeopleBean> contactsPeoeleListData);
	}

	public static void getGroupList(Context context, Handler handler){
		String[] projection = new String[]{ContactsContract.Groups._ID,ContactsContract.Groups.TITLE};
		//String where = ContactsContract.RawContacts.CONTACT_ID + "=?";
		groupList = new ArrayList<ContactsGroup>();
		Cursor cursor = context.getContentResolver().query(ContactsContract.Groups.CONTENT_URI, projection, null, null, null);
		if(cursor != null){
			while(cursor.moveToNext()){
				String id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Groups._ID)) + "";
				String title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE)) + "";
				ContactsGroup contactsGroup = new ContactsGroup();
				contactsGroup.setGroup_id(id);
				contactsGroup.setGroup_name(title);
				groupList.add(contactsGroup);
			}
			cursor.close();
			Message msg = new Message();
			msg.what = 1;
			handler.sendMessage(msg);
		}
	}

	public static int deleteCallLog(Context context, ArrayList<String> idList){
		int row = 0;
		if(idList != null && idList.size() > 0){
			String[] selecteargs = new String[idList.size()];
			StringBuffer where = new StringBuffer();
			for (int i = 0; i < idList.size(); ++i){
				selecteargs[i] = idList.get(i);
				where.append("_id=? or ");
			}
			where.delete(where.length() - 4, where.length());
			//String sql = "delete from "
			ContentResolver resolver = context.getContentResolver();  
			row = resolver.delete(CallLog.Calls.CONTENT_URI, where.toString(), selecteargs);  
		}
		return row;
	}

	public static Uri addCallLog(Context context, NewCallLogBean logBean){
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, logBean.getNumber());
		values.put(CallLog.Calls.DATE, logBean.getDate());
		values.put(CallLog.Calls.DURATION, logBean.getDuration());
		values.put(CallLog.Calls.TYPE, logBean.getType());
		values.put(CallLog.Calls.CACHED_NAME, logBean.getName());
		values.put(CallLog.Calls.CACHED_NUMBER_TYPE, logBean.getNumbertype());
		values.put(CallLog.Calls.CACHED_NUMBER_LABEL, logBean.getNumberlabel());
		values.put(CallLog.Calls.IS_READ, logBean.getIsRead());
		values.put(CallLog.Calls.GEOCODED_LOCATION, logBean.getGeocoded_location());
		Uri newUri =  context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
		return newUri;
	}

	public static int updataCallLog(Context context, NewCallLogBean logBean){
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, logBean.getNumber());
		values.put(CallLog.Calls.DATE, logBean.getDate());
		values.put(CallLog.Calls.DURATION, logBean.getDuration());
		values.put(CallLog.Calls.TYPE, logBean.getType());
		values.put(CallLog.Calls.CACHED_NAME, logBean.getName());
		values.put(CallLog.Calls.CACHED_NUMBER_TYPE, logBean.getNumbertype());
		values.put(CallLog.Calls.CACHED_NUMBER_LABEL, logBean.getNumberlabel());
		values.put(CallLog.Calls.IS_READ, logBean.getIsRead());
		values.put(CallLog.Calls.GEOCODED_LOCATION, logBean.getGeocoded_location());
		String where = CallLog.Calls.NUMBER + "=? AND " + CallLog.Calls.DATE + "=?";
		String[] select = new String[]{logBean.getNumber(), logBean.getDate() + ""};
		int row =  context.getContentResolver().update(CallLog.Calls.CONTENT_URI, values, where, select);
		return row;
	}

	public static CallLogBean getCallLogByUri(Uri uri){
		//获得一个ContentResolver数据共享的对象
		ContentResolver reslover = context.getContentResolver();
		ArrayList<CallLogBean> callLogBeanList = new ArrayList<CallLogBean>();
		//取得联系人中开始的游标，通过content://com.android.contacts/contacts这个路径获得
		//String[] index = new String[]{android.provider.CallLog.Calls._ID, android.provider.ContactsContract.Data.DATA1};
		Cursor cursor = reslover.query(uri, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
		while(cursor.moveToNext()){
			CallLogBean callLogBean = new CallLogBean();
			callLogBean.setCallogId(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls._ID)));
			String name = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
			String number = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
			callLogBean.setCallLogdate(cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE)));
			callLogBean.setDataBean(DataConverterUtil.getData(Long.parseLong(callLogBean.getCallLogdate())));
			callLogBean.setDuration(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION)));
			callLogBean.setLogType(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE)));
			callLogBean.setNumberType(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NUMBER_TYPE)));
			String phoneLocation = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.GEOCODED_LOCATION));
			if(phoneLocation != null && !phoneLocation.equals("")){
				callLogBean.setNumberAdress(cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.GEOCODED_LOCATION)));
			}
			if(name != null && !name.equals("")){
				callLogBean.setName(name);
			}
			if(number != null && !number.equals("")){
				callLogBean.setNumber(number);
			}
			callLogBean.setItemType(0);
			callLogBeanList.add(callLogBean);
			return callLogBean;
		}
		cursor.close();
		return null;
	}


	public static int setContactToFavorite(Context context, String value, String contactId){
		ContentValues values = new ContentValues();  
		values.put(ContactsContract.RawContacts.STARRED, value);
		String Where = ContactsContract.RawContacts.CONTACT_ID + "=?";  
		String[] WhereParams = new String[]{contactId};
		int updataState = context.getContentResolver().update(ContactsContract.RawContacts.CONTENT_URI,   
				values, Where, WhereParams);
		return updataState;
	}

	public static String getContactToFavoriteType(Context context, String contactId){
		String Where = ContactsContract.RawContacts.CONTACT_ID + "=?"; 
		String[] projection = new String[]{ContactsContract.RawContacts.STARRED};
		String[] WhereParams = new String[]{contactId};
		String where = ContactsContract.RawContacts.CONTACT_ID + "=?";
		Cursor cursor = context.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, projection, where, WhereParams, null);
		if(cursor != null){
			while(cursor.moveToNext()){
				String streedType = cursor.getInt(cursor.getColumnIndex(ContactsContract.RawContacts.STARRED)) + "";
				cursor.close();
				return streedType;
			}
			cursor.close();
		}
		return "-1";
	}

	private static String getContactId(String numberKeyWord) {
		// TODO Auto-generated method stub
		/*String s = "%";
		char[] numChar = numberKeyWord.toCharArray();
		if(numberKeyWord != null && numberKeyWord.length() > 0){
			for (int i = 0; i < numChar.length; ++i){
				s = s +  numChar[i] + "%";

			}
			Log.e("c", s);
		}else{
			return "";
		}*/
		String where =ContactsContract.Data.DATA1 + "= ? AND " + ContactsContract.Data.MIMETYPE + "=?"; 
		String[] projection = new String[]{ContactsContract.Data.CONTACT_ID, ContactsContract.Data.MIMETYPE, ContactsContract.Data.DATA1};
		String[] WhereParams = new String[]{numberKeyWord, MimetypeString.mimetype_phone};
		//String where = ContactsContract.RawContacts.CONTACT_ID + "=?";
		Cursor cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, where, WhereParams, null);
		if(cursor != null){
			while(cursor.moveToNext()){
				String contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)) + "";
				Log.e("查询联系人数据ID:", contactId);
				return contactId;
			}
			cursor.close();
		}
		return "";
	}

	public static ArrayList<String> getContactNumberListByContactId(Context context1, String contactId){
		Cursor phone =  context1.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);

		ArrayList<String> numberList = new ArrayList<String>();
		if(phone != null){
			while(phone.moveToNext()){ //取得电话号码(可能存在多个号码)
				int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
				String phoneNumber = phone.getString(phoneFieldColumnIndex);
				//phoneNumber.replace(" ", "");
				numberList.add(phoneNumber);
			}
			phone.close();
			return numberList;
		}
		return null;
	}

	public static void getContactInfoByCountIdONumber(final int keyWordType, final String contactIdKeyWord, final String numberKeyWord, final Handler handler){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(keyWordType == 0 && contactIdKeyWord != null && !contactIdKeyWord.equals("")){
					getNumberByNameOrNumbersubMether(contactIdKeyWord, handler);
				}else if(keyWordType == 1 && numberKeyWord != null && !numberKeyWord.equals("")){
					String contactId = getContactId(numberKeyWord);
					if(contactId != null && !contactId.equals("")){
						getNumberByNameOrNumbersubMether(contactId, handler);
					}else{
						Message message = new Message();
						Bundle bundle = new Bundle();
						infomationBean = new ContactInfomationBean();
						ArrayList<NumberInfomation> numberList = new ArrayList<NumberInfomation>();
						NumberInfomation infoBean = new NumberInfomation();
						infoBean.setNumber(numberKeyWord);
						infoBean.setNumberDescript("");
						numberList.add(infoBean);
						ArrayList<EmaiInfomation> emailList = new ArrayList<EmaiInfomation>();
						ArrayList<DataInfomation> dataList = new ArrayList<DataInfomation>();
						ArrayList<AdressInfomation> adressList = new ArrayList<AdressInfomation>();
						infomationBean.setNumberList(numberList);
						infomationBean.setDataList(dataList);
						infomationBean.setAdressList(adressList);
						infomationBean.setEmailList(emailList);
						message.setData(bundle);
						handler.sendMessage(message);
					}
				}
			}

			private void getNumberByNameOrNumbersubMether(final String keyWord, Handler handler) {
				Message message = new Message();
				Bundle bundle = new Bundle();
				infomationBean = new ContactInfomationBean();
				ArrayList<NumberInfomation> numberList = new ArrayList<NumberInfomation>();
				ArrayList<EmaiInfomation> emailList = new ArrayList<EmaiInfomation>();
				ArrayList<DataInfomation> dataList = new ArrayList<DataInfomation>();
				ArrayList<AdressInfomation> adressList = new ArrayList<AdressInfomation>();

				// TODO Auto-generated method stub
				Log.e("c", "查询关键词:" + keyWord);
				String[] selectArgs = new String[]{keyWord};
				/*Cursor dataCouse =  ContactsUtil.context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
						ContactsContract.Data.RAW_CONTACT_ID + "=?", selectArgs, null);*/
				Cursor dataCouse =  ContactsUtil.context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
						ContactsContract.Data.CONTACT_ID + "=?", selectArgs, null);
				if(dataCouse != null){
					Log.e("c", "查询结果不为空");
					while(dataCouse.moveToNext()){
						Log.e("c", "查询数据不为空");
						String Mimetype = dataCouse.getString(dataCouse.getColumnIndex(ContactsContract.Data.MIMETYPE));//获取Mimetype

						infomationBean.setContact_id(dataCouse.getInt(dataCouse.getColumnIndex(ContactsContract.Data.CONTACT_ID)) + "");	//联系人ID
						Log.e("联系人ID：", dataCouse.getInt(dataCouse.getColumnIndex(ContactsContract.Data.CONTACT_ID)) + "");
						String data = dataCouse.getString(dataCouse.getColumnIndex(ContactsContract.Data.DATA1));
						if(Mimetype.equals(MimetypeString.mimetype_email)){	//邮箱
							EmaiInfomation emaiIn = new EmaiInfomation();
							emaiIn.setEmailAdress(data);
							String type = dataCouse.getString(dataCouse.getColumnIndex(ContactsContract.Data.DATA2));
							if(type != null && !type.equals("")){
								emaiIn.setEmailType(type);
							}
							emailList.add(emaiIn);
						}else if(Mimetype.equals(MimetypeString.mimetype_phone)){//电话
							NumberInfomation numberInfomation = new NumberInfomation();
							numberInfomation.setNumber(data);
							String type = dataCouse.getString(dataCouse.getColumnIndex(ContactsContract.Data.DATA2));
							if(type != null && !type.equals("")){
								numberInfomation.setNumberDescript(type);
							}
							numberList.add(numberInfomation);
						}else if(Mimetype.equals(MimetypeString.mimetype_nickname)){//		//昵称
							infomationBean.setNikeName(data);
						}else if(Mimetype.equals(MimetypeString.mimetype_organization)){//		公司
							infomationBean.setCompany(data);
							String type = dataCouse.getString(dataCouse.getColumnIndex(ContactsContract.Data.DATA4));
							if(type != null && !type.equals("")){
								infomationBean.setJob(type);
							}
						}else if(Mimetype.equals(MimetypeString.mimetype_sipaddress)){//	//?

						}else if(Mimetype.equals(MimetypeString.mimetype_name)){//	名字
							infomationBean.setName(data);
						}else if(Mimetype.equals(MimetypeString.mimetype_postaladdress)){//	地址
							AdressInfomation adressInfomation = new AdressInfomation();
							adressInfomation.setAdress(data);
							String type = dataCouse.getString(dataCouse.getColumnIndex(ContactsContract.Data.DATA2));
							if(type != null && !type.equals("")){
								adressInfomation.setAdressType(type);
							}
							adressList.add(adressInfomation);
						}else if(Mimetype.equals(MimetypeString.mimetype_photo)){//

						}else if(Mimetype.equals(MimetypeString.mimetype_contactevent)){		//联系人日期
							DataInfomation dataInfomation = new DataInfomation();
							dataInfomation.setData(data);
							String type = dataCouse.getString(dataCouse.getColumnIndex(ContactsContract.Data.DATA2));
							if(type != null && !type.equals("")){
								dataInfomation.setDataType(type);
							}
							dataList.add(dataInfomation);
						}else if(Mimetype.equals(MimetypeString.mimetype_note)){		//备注
							infomationBean.setNote(data);
						}else if(Mimetype.equals(MimetypeString.mimetype_website)){ 		//网站
							infomationBean.setWebSite(data);
						}else if(Mimetype.equals(MimetypeString.mimetype_groupmembership)){	//分组
							infomationBean.setGroup(data);
						}
					}
					infomationBean.setNumberList(numberList);
					infomationBean.setDataList(dataList);
					infomationBean.setAdressList(adressList);
					infomationBean.setEmailList(emailList);
					message.setData(bundle);
					dataCouse.close();
					handler.sendMessage(message);
				}
				dataCouse.close();
			}
		}).start();
	}



	/**
	 * 对联系人列表进行拼音分类
	 * @param arrayList
	 * @return
	 */
	public static ArrayList<ContactPeopleBean> groupByPinyFirstChar(ArrayList<ContactPeopleBean> arrayList){
		StaticObject.pinyinIndexMap = new HashMap();
		if(arrayList != null && arrayList.size() > 0){
			for (int i = 0; i < arrayList.size(); ++i){
				if(i == 0 || i == 1){
					arrayList.get(i).setIsFirstItemShowChar("");
					arrayList.get(i).setNamePinYFirstChar("#");
					StaticObject.pinyinIndexMap.put("#", 0);
				}else{
					if(arrayList.get(i).getNamePinyin() != null && !arrayList.get(i).getNamePinyin().equals("")){
						String firstChar = arrayList.get(i).getNamePinyin().substring(0, 1);
						if((firstChar.compareTo("Z") <= 0 && firstChar.compareTo("A") >= 0) || 
								(firstChar.compareTo("z") <= 0 && firstChar.compareTo("a") >= 0)){
							arrayList.get(i).setNamePinYFirstChar(firstChar.toUpperCase());
							if(!arrayList.get(i).getNamePinYFirstChar().equals(arrayList.get(i - 1).getNamePinYFirstChar())){
								arrayList.get(i).setIsFirstItemShowChar(firstChar.toUpperCase());
								StaticObject.pinyinIndexMap.put(firstChar.toUpperCase(), i);
							}
						}else{
							arrayList.get(i).setNamePinYFirstChar("#");
						}
					}else{
						arrayList.get(i).setNamePinYFirstChar("#");
						if(!arrayList.get(i).getNamePinYFirstChar().equals(arrayList.get(i - 1).getNamePinYFirstChar())){
							arrayList.get(i).setIsFirstItemShowChar("#");
							StaticObject.pinyinIndexMap.put("#", i);
						}
					}
				}
			}
		}
		return arrayList;

	}

	public static void getCallLogByNumber(final Context context1, final String number, final Handler handler){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String contactId = getContactId(number);
				ArrayList<String> numberList = null;
				ArrayList<ContactInfomationCalllogItemBean> logList = new ArrayList<ContactInfomationCalllogItemBean>();
				if(contactId != null && !contactId.equals("")){
					numberList = getContactNumberListByContactId(context1, contactId);
					if(numberList != null && numberList.size() > 0){
						for (String n: numberList){
							logList = getLogByNumber(context1, number, logList);
						}
					}else{
						logList = getLogByNumber(context1, number, logList);
					}
				}else{
					logList = getLogByNumber(context1, number, logList);
				}
				ContactsUtil.subcalllogList = logList;
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}

			protected ArrayList<ContactInfomationCalllogItemBean> getLogByNumber(Context context1, String number, ArrayList<ContactInfomationCalllogItemBean> logList){
				Uri uri = CallLog.Calls.CONTENT_URI;
				ContentResolver reslover = context1.getContentResolver();
				//ArrayList<CallLogBean> callLogBeanList = new ArrayList<CallLogBean>();
				//取得联系人中开始的游标，通过content://com.android.contacts/contacts这个路径获得
				String where = CallLog.Calls.NUMBER + "=?";
				String[] selectArgs = new String[]{number};
				//String[] index = new String[]{android.provider.CallLog.Calls._ID, android.provider.ContactsContract.Data.DATA1};
				Cursor cursor = reslover.query(uri, null, where, selectArgs, android.provider.CallLog.Calls.DATE + " DESC");
				if(cursor == null){
					return logList;
				}
				while(cursor.moveToNext()){
					ContactInfomationCalllogItemBean callLogBean = new ContactInfomationCalllogItemBean();
					callLogBean.setCallogId(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls._ID)));
					String name = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
					//String num = number;
					callLogBean.setCallLogdate(cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE)));
					callLogBean.setDataBean(DataConverterUtil.getData(Long.parseLong(callLogBean.getCallLogdate())));
					callLogBean.setDuration(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION)));
					callLogBean.setLogType(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE)));
					callLogBean.setNumberType(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NUMBER_TYPE)));
					String phoneLocation = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.GEOCODED_LOCATION));
					if(phoneLocation != null && !phoneLocation.equals("")){
						callLogBean.setNumberAdress(cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.GEOCODED_LOCATION)));
					}
					if(name != null && !name.equals("")){
						callLogBean.setName(name);
					}
					if(number != null && !number.equals("")){
						callLogBean.setNumber(number);
					}
					callLogBean.setItemType(0);
					logList.add(callLogBean);
				}
				cursor.close();
				return logList;
			};
		}).start();
	}


	public static ArrayList<CallLogBean> getCallLogList(Context context){
		//获得一个ContentResolver数据共享的对象
		Uri uri = Uri.parse("content://call_log/calls");
		ContentResolver reslover = context.getContentResolver();
		ArrayList<CallLogBean> callLogBeanList = new ArrayList<CallLogBean>();
		//取得联系人中开始的游标，通过content://com.android.contacts/contacts这个路径获得
		//String[] index = new String[]{android.provider.CallLog.Calls._ID, android.provider.ContactsContract.Data.DATA1};
		Cursor cursor = reslover.query(uri, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
		while(cursor.moveToNext()){
			CallLogBean callLogBean = new CallLogBean();
			callLogBean.setCallogId(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls._ID)));
			String name = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
			String number = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
			callLogBean.setCallLogdate(cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE)));
			callLogBean.setDataBean(DataConverterUtil.getData(Long.parseLong(callLogBean.getCallLogdate())));
			callLogBean.setDuration(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION)));
			callLogBean.setLogType(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE)));
			callLogBean.setNumberType(cursor.getInt(cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NUMBER_TYPE)));
			String phoneLocation = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.GEOCODED_LOCATION));
			if(phoneLocation != null && !phoneLocation.equals("")){
				callLogBean.setNumberAdress(cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.GEOCODED_LOCATION)));
			}
			if(name != null && !name.equals("")){
				callLogBean.setName(name);
			}
			if(number != null && !number.equals("")){
				callLogBean.setNumber(number);
			}
			callLogBean.setItemType(0);
			callLogBeanList.add(callLogBean);
		}
		cursor.close();
		return callLogBeanList;
		//String id = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID)); 
		/*while(cursor.moveToNext()){
			//获得联系人ID
			String id = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID)); 
			//获得联系人姓名
			String name = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME));
			//获得联系人手机号码
			Cursor phone =   reslover.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);

			StringBuilder sb = new StringBuilder("contactid=").append(id).append(name);
			while(phone.moveToNext()){ //取得电话号码(可能存在多个号码)
				int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
				String phoneNumber = phone.getString(phoneFieldColumnIndex);
				sb.append(phoneNumber+"www");
			}
			//建立一个Log，使得可以在LogCat视图查看结果
			Log.i("c", sb.toString());
		}*/

	}


	public static void getContacts(final Context context, final contactsCallBack callBack){
		MyAsyncQueryHandler asyncQuery = new MyAsyncQueryHandler(context.getContentResolver());  
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		//String[] projection = {"_id", "display_name", "data1", "sort_key"};  
		ContactsUtil.context = context;
		asyncQuery.startToQuery(asyncQuery, 0, null, uri, null, null, null,  
				"sort_key COLLATE LOCALIZED asc", new handlerQueryCallBack() {

			@Override
			public void callBack(Cursor cursor) {
				// TODO Auto-generated method stub
				final Cursor cursor1 = cursor;
				new Thread(new Runnable() {

					@Override
					public void run() {
						Message message = new Message();
						Bundle bundle = new Bundle();
						// TODO Auto-generated method stub
						ArrayList<ContactPeopleBean> peopleListData = new ArrayList<ContactPeopleBean>();
						if(cursor1 != null){
							while(cursor1.moveToNext()){
								ContactPeopleBean contactPeopleBean = new ContactPeopleBean();
								contactPeopleBean.setContactId(cursor1.getInt(cursor1.getColumnIndex(android.provider.ContactsContract.Contacts._ID)));
								Log.e("c", contactPeopleBean.getContactId() + ":contact_id");
								String name = cursor1.getString(cursor1.getColumnIndex(android.provider.ContactsContract.Contacts.DISPLAY_NAME)); 
								if(name != null){
									//Log.e("c", "name:" + name);
									contactPeopleBean.setName(name);
								}
								String pingYingStr = Cn2Spell.getPinYin(name);
								contactPeopleBean.setNamePinyin(pingYingStr);
								contactPeopleBean.setStarred(cursor1.getInt(cursor1.getColumnIndex(android.provider.ContactsContract.Contacts.STARRED)));
								contactPeopleBean.setContactCoverId(cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_ID)));
								if(contactPeopleBean.getContactCoverId() != null && !contactPeopleBean.getContactCoverId().equals("")){
									contactPeopleBean.setContactCoverBitmap(BitmapUtil.getPhoto(context, contactPeopleBean.getContactId() + ""));
									contactPeopleBean.setConverColor(ConverBitmapSourse.converBitmapColor[0]);
								}else{
									int chosePotion = contactPeopleBean.getContactId() % 4;
									//int chosePotion = (int) (Math.random() * 4);
									contactPeopleBean.setConverColor(ConverBitmapSourse.converBitmapColor[chosePotion]);
									contactPeopleBean.setContactCoverBitmap(BitmapFactory.decodeResource(context.getResources(), 
											ConverBitmapSourse.converBitmapSourse[chosePotion]));
								}
								peopleListData.add(contactPeopleBean);
							}
							cursor1.close();
						}
						StaticObject.back = callBack;
						StaticObject.peopleAllListData = peopleListData;
						bundle.putString("ok", "ok");
						message.setData(bundle);
						handler.sendMessage(message);
					}
				}).start();
			}
		});
	}

	private static Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle data = msg.getData();
			String isok = data.getString("ok");
			startToQuareNumberByThread();
			StaticObject.back.allContactsCuror(StaticObject.peopleAllListData);
		}
	};
	private static void startToQuareNumberByThread() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(StaticObject.peopleAllListData != null){
					for(int i = 0; i < StaticObject.peopleAllListData.size(); ++i){
						Cursor phone =  ContactsUtil.context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + StaticObject.peopleAllListData.get(i).getContactId(), null, null);

						ArrayList<String> numberList = new ArrayList<String>();
						if(phone != null){
							while(phone.moveToNext()){ //取得电话号码(可能存在多个号码)
								int phoneFieldColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
								String phoneNumber = phone.getString(phoneFieldColumnIndex);
								//phoneNumber.replace(" ", "");
								numberList.add(phoneNumber);
							}
							phone.close();
							StaticObject.peopleAllListData.get(i).setNumberList(numberList);
						}
					}
				}
				Log.e("c", "号码查询完成");
			}
		}).start();
	}


	public static ArrayList<ContactPeopleBean> getSterredList(ArrayList<ContactPeopleBean> allContacts){
		ArrayList<ContactPeopleBean> sterred = new ArrayList<ContactPeopleBean>();
		if(allContacts == null || allContacts.size() == 0){
			return sterred; 
		}
		for (ContactPeopleBean c:allContacts){
			if(c.getStarred() == 1){
				sterred.add(c);
			}
		}
		return sterred;
	}
}

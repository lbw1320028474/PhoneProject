package com.example.contacts;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class MyAsyncQueryHandler extends AsyncQueryHandler {
	public handlerQueryCallBack queryCallBack = null;
	public MyAsyncQueryHandler(ContentResolver cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	public interface handlerQueryCallBack{
		public void callBack(Cursor cursor);
	}

	public void startToQuery(MyAsyncQueryHandler asyncQuery, int token, Object cookie, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy, handlerQueryCallBack queryCallBack){
		this.queryCallBack = queryCallBack;
		asyncQuery.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);
	}

	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		// TODO Auto-generated method stub
		super.onQueryComplete(token, cookie, cursor);
		this.queryCallBack.callBack(cursor);
		/*if (cursor != null && cursor.getCount() > 0) {  
			cursor.moveToFirst();  
			for (int i = 0; i < cursor.getCount(); i++) {  
				cursor.moveToPosition(i);  
				String name = cursor.getString(1);//�绰��  
				String number = cursor.getString(2);//�绰��  
				String newNumber = number.replace(" ", "");// ȡ�����ĵ绰�����еĻ��пո�  
				if (newNumber.startsWith("+86")) {  
					numbers.put(newNumber.substring(3), name);  
				} else {  
					numbers.put(newNumber, name);  
				}  
			}  
		}  */
	}

}

package com.example.contacts;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class ContactChangeListener extends ContentObserver {
	private static final String TAG = "ContactChangeListener";
	private Context context = null;
	private Handler handler = null;
	public ContactChangeListener(Context context,Handler handler) {
		super(handler);
		this.context = context;
		this.handler = handler;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onChange(boolean selfChange, Uri uri) {
		// TODO Auto-generated method stub
		super.onChange(selfChange, uri);
		Log.e(TAG, uri.toString());
	}

}

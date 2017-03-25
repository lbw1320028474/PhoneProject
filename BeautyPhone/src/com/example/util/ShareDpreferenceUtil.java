package com.example.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareDpreferenceUtil {
	public void setShareData(Context context, String key, String value){
		SharedPreferences preferences = context.getSharedPreferences("beautyxml", context.MODE_WORLD_READABLE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getShareData(Context context, String key){
		SharedPreferences preferences = context.getSharedPreferences("beautyxml", context.MODE_WORLD_READABLE);
		Editor editor = preferences.edit();
		return preferences.getString(key, "");
	}
}

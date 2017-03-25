package com.example.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtil {
	private ScreenUtil(){
	}
	private static Context context = null;
	private static ScreenUtil screenUtil = null;
	public static ScreenUtil getScreenUtil(Context context){
		if(screenUtil == null){
			screenUtil = new ScreenUtil();
		}
		ScreenUtil.context = context;
		return screenUtil;
	}

	public int getScreenWidth(){
		WindowManager manager = (WindowManager)ScreenUtil.context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		return width;

	}

	public int getScreenHeight(){
		WindowManager manager = (WindowManager)ScreenUtil.context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int height = display.getHeight();
		return height;

	}
}

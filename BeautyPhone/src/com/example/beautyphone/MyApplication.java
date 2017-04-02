package com.example.beautyphone;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.listener.SMSCodeListener;

import com.example.beautyphone.FYSdk.FYsdkInit;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import c.b.BP;

public class MyApplication extends Application {
	/** 应用上下文对象 */
	public static Context AppCtx;
	private String appId = "4a7a38e788d822c10bbe985f5280ddde";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//第二个参数是渠道号
		//创建默认的ImageLoader配置参数  
		BP.init(appId);
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration  
				.createDefault(this);  
		ImageLoader.getInstance().init(configuration);  
		FYsdkInit.initFYsdk(getApplicationContext(), "test");
		BmobSMS.initialize(getApplicationContext(), MyApplicationInfo.BMOB_SMS_APPID);
	}
}

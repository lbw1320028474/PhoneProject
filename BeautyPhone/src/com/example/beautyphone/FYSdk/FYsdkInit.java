package com.example.beautyphone.FYSdk;

import com.feiyucloud.sdk.FYClient;
import com.feiyucloud.sdk.FYClientListener;
import com.feiyucloud.sdk.FYError;

import android.content.Context;
import android.util.Log;

public class FYsdkInit{
	public static void initFYsdk(Context context, String channeId){
		FYClient.instance().init(context, channeId, false, false);
		/*FYClient.addListener(clientListener);
		String account = "FYB1BBCVAJ33D";
		String password = "U7IIDJ";
		FYClient.instance().connect(FYAppData.getAPPID(),FYAppData.getAPPTOKEN(), account, password);*/
	}
}

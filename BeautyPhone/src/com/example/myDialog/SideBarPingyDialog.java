package com.example.myDialog;

import com.example.beautyphone.R;
import com.example.util.ScreenUtil;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SideBarPingyDialog{
	public boolean isShow = false;
	private TextView textView = null;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private Dialog pinyinDialog = null;
	public SideBarPingyDialog(Context context){
		pinyinDialog = new Dialog(context, R.style.pingy_dialogstyle);
		pinyinDialog.setCancelable(true);
		Window dialogWindow = pinyinDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
		View contentView = LinearLayout.inflate(context, R.layout.pingyin_dialog_layout, null);
		textView = (TextView) contentView.findViewById(R.id.sidebar_pingyin_text);
		screenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();  
		screenHeight = ScreenUtil.getScreenUtil(context).getScreenHeight(); 
		lp.x = 50;
		lp.y = screenHeight/3;
		dialogWindow.setAttributes(lp);
		pinyinDialog.getWindow().setAttributes(lp);  

		pinyinDialog.setContentView(contentView, new LinearLayout.LayoutParams(  
				screenWidth / 3,
				screenHeight/2));
	};

	public void showDialog(String s){
		textView.setText(s);
		if(!isShow){
			pinyinDialog.show();
			isShow = true;
		}
	}

	public void closeDialog(){
		if(isShow){
			isShow = false;
			pinyinDialog.dismiss();
		}
	}
}

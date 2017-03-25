package com.example.myDialog;

import java.util.Timer;
import java.util.TimerTask;

import com.example.beautyphone.R;
import com.example.util.ScreenUtil;
import com.example.util.SimUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class LoadingDialog{
	public boolean isShow = false;
	private Context context = null;
	private float screenWidth = 0;
	private float screenHeight = 0;
	private ImageView dialog_bg = null;
	private ImageView fram1 = null;
	private ImageView fram2 = null;
	private ImageView fram3 = null;
	private ImageView fram4 = null;
	private ImageView fram5 = null;
	private ImageView fram6 = null;
	private int animcount = 0;
	private ImageView fram7 = null;
	private Dialog callTypeChoseDialog = null;

	private void initView(View contentView) {
		// TODO Auto-generated method stub
		dialog_bg = (ImageView)contentView.findViewById(R.id.loading_dialog_bg);
		fram1 = (ImageView)contentView.findViewById(R.id.loading_dialog_1);
		fram2 = (ImageView)contentView.findViewById(R.id.loading_dialog_2);
		fram3 = (ImageView)contentView.findViewById(R.id.loading_dialog_3);
		fram4 = (ImageView)contentView.findViewById(R.id.loading_dialog_4);
		fram5 = (ImageView)contentView.findViewById(R.id.loading_dialog_5);
		fram6 = (ImageView)contentView.findViewById(R.id.loading_dialog_6);
		fram7 = (ImageView)contentView.findViewById(R.id.loading_dialog_7);
	}

	public LoadingDialog(Context context){
		this.context = context;
		callTypeChoseDialog = new Dialog(context, R.style.loading_dialogstyle);
		callTypeChoseDialog.setCancelable(false);
		callTypeChoseDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				isShow = false;
			}
		});
		//dialogWindow.setGravity(Gravity.BOTTOM);
		View contentView = LinearLayout.inflate(context, R.layout.load_dialog_layout, null);
		initView(contentView);
		screenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		screenHeight = ScreenUtil.getScreenUtil(context).getScreenHeight();
		callTypeChoseDialog.setContentView(contentView, new LinearLayout.LayoutParams(  
				(int) screenWidth,
				(int) screenHeight));
	};


	public void showDialog(){
		if(!isShow){
			setmage_size();
			callTypeChoseDialog.show();
			startAnim();
			isShow = true;
			Timer timer = new Timer();
			final Handler handler = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if(isShow){
						super.handleMessage(msg);
						Toast.makeText(context, "²Ù×÷³¬Ê±", 0).show();
						closeDialog();
					}
				}
			};
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.sendMessage(new Message());
				}
			}, 10000);
		}
	}

	private void startAnim() {
		startAnimByTime(fram4, 0);
		startAnimByTime(fram3, 200);
		startAnimByTime(fram2, 400);
		startAnimByTime(fram1, 600);
		startAnimByTime(fram5, 800);
		startAnimByTime(fram6, 1000);
		startAnimByTime(fram7, 1200);
	}

	private void startAnimByTime(final View v, long time){
		final Animation animBig = AnimationUtils.loadAnimation(context, R.anim.load_anim_big);
		final Animation animSmall = AnimationUtils.loadAnimation(context, R.anim.load_anim_small);
		Timer timer = new Timer();
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				v.startAnimation(animBig);
			}
		};
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				handler.sendMessage(message);
			}
		}, time);
	}


	private void setmage_size() {
		int width = (int) (screenWidth * 0.0315) + 10;
		int bg_height = (int)(screenHeight * 0.2146);
		int bg_width = (int)(screenWidth * 0.76);
		android.widget.RelativeLayout.LayoutParams layoutParams = null;
		layoutParams = (android.widget.RelativeLayout.LayoutParams) dialog_bg.getLayoutParams();
		layoutParams.width = (int) bg_width;
		layoutParams.height = (int) bg_height;
		dialog_bg.setLayoutParams(layoutParams);

		layoutParams = (android.widget.RelativeLayout.LayoutParams) fram1.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		fram1.setLayoutParams(layoutParams);

		layoutParams = (android.widget.RelativeLayout.LayoutParams) fram2.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		fram2.setLayoutParams(layoutParams);

		layoutParams = (android.widget.RelativeLayout.LayoutParams) fram3.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		fram3.setLayoutParams(layoutParams);

		layoutParams = (android.widget.RelativeLayout.LayoutParams) fram4.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		fram4.setLayoutParams(layoutParams);

		layoutParams = (android.widget.RelativeLayout.LayoutParams) fram5.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		fram5.setLayoutParams(layoutParams);

		layoutParams = (android.widget.RelativeLayout.LayoutParams) fram6.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		fram6.setLayoutParams(layoutParams);

		layoutParams = (android.widget.RelativeLayout.LayoutParams) fram7.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = width;
		fram7.setLayoutParams(layoutParams);
	}

	public void closeDialog(){
		if(isShow){
			isShow = false;
			callTypeChoseDialog.dismiss();
		}
	}


}

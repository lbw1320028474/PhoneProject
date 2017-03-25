package com.example.myDialog;

import com.example.beautyphone.R;
import com.example.util.ScreenUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class TipDialog implements OnClickListener{
	private static TipDialog tipDialog= null;
	public boolean isShow = false;
	private Context context = null;
	private float screenWidth = 0;
	private float screenHeight = 0;
	private TextView tipTextView = null;
	private ImageView bgView = null;
	private Button okButton = null;
	private Button cancleButton = null;
	private Dialog callTypeChoseDialog = null;
	private tipDialotButtonListener buttonListener = null;

	private void initView(View contentView) {
		// TODO Auto-generated method stub
		bgView = (ImageView)contentView.findViewById(R.id.tip_dialog_bg);
		okButton = (Button)contentView.findViewById(R.id.tip_dialog_ok);
		tipTextView = (TextView)contentView.findViewById(R.id.tip_dialog_tiptext);
		cancleButton = (Button)contentView.findViewById(R.id.tip_dialog_cancle);
		okButton.setOnClickListener(this);
		cancleButton.setOnClickListener(this);
	}

	public TipDialog(Context context){
		this.context = context;
		callTypeChoseDialog = new Dialog(context, R.style.loading_dialogstyle);
		callTypeChoseDialog.setCancelable(true);
		callTypeChoseDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				isShow = false;
			}
		});
		//dialogWindow.setGravity(Gravity.BOTTOM);
		View contentView = LinearLayout.inflate(context, R.layout.tip_dialog_layout, null);
		initView(contentView);
		screenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		screenHeight = ScreenUtil.getScreenUtil(context).getScreenHeight();
		callTypeChoseDialog.setContentView(contentView, new LinearLayout.LayoutParams(  
				(int) screenWidth,
				(int) screenHeight));
	};


	public void showDialog(String tip, tipDialotButtonListener listener){
		if(!isShow){
			this.buttonListener = listener;
			setmage_size();
			tipTextView.setText(tip);
			callTypeChoseDialog.show();
			isShow = true;
		}
	}



	private void setmage_size() {
		int bg_height = (int)(screenHeight * 0.2146);
		int bg_width = (int)(screenWidth * 0.76);
		android.widget.RelativeLayout.LayoutParams layoutParams = null;
		layoutParams = (android.widget.RelativeLayout.LayoutParams) bgView.getLayoutParams();
		layoutParams.width = (int) bg_width;
		layoutParams.height = (int) bg_height;
		bgView.setLayoutParams(layoutParams);

	}

	public void closeDialog(){
		if(isShow){
			isShow = false;
			callTypeChoseDialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tip_dialog_ok:
			if(buttonListener != null){
				buttonListener.click(true);
			}
			break;
		case R.id.tip_dialog_cancle:
			buttonListener.click(false);
			break;
		}
		closeDialog();
	}

	public interface tipDialotButtonListener{
		public void click(boolean isOk);
	}

}

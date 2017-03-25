package com.example.myDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.activity.IsCallIngActivity;
import com.example.activity.LoginActivity;
import com.example.activity.UserLoginUtil;
import com.example.beautyphone.MainActivity;
import com.example.beautyphone.R;
import com.example.util.ScreenUtil;
import com.example.util.SimUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CallTypeChoseDialog implements OnClickListener{
	public boolean isShow = false;
	private TextView setButton = null;
	private Button free_callButton = null;
	private Button normal_callButtonSim1 = null;
	private Button normal_callButtonSim2 = null;
	private Button cancale_button = null;
	private String callNumber = "";
	private String callName = "";
	private String simType = "";
	private int screenWidth = 0;
	private ArrayList<SimUtil.SimInfomation> simList = null;
	private LinearLayout rootView = null;
	private Context context = null;
	private int screenHeight = 0;
	private Dialog callTypeChoseDialog = null;
	public CallTypeChoseDialog(Context context){
		this.context = context;
		/*String simi = SimUtil.readSIMCard(context);
		Log.e("c", simi);*/
		//Toast.makeText(context, simi, 0).show();
		SimUtil.getInstance().initConfig(context);
		callTypeChoseDialog = new Dialog(context, R.style.pingy_dialogstyle);
		callTypeChoseDialog.setCancelable(true);
		Window dialogWindow = callTypeChoseDialog.getWindow();
		dialogWindow.setWindowAnimations(R.style.CallTypeChoseDialogAnim);
		callTypeChoseDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				isShow = false;
			}
		});
		//WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.BOTTOM);
		View contentView = LinearLayout.inflate(context, R.layout.calltypechose_dialog_layout, null);
		rootView = (LinearLayout)contentView.findViewById(R.id.calltypechose_dialog_normalcall_rootview);
		rootView.setVisibility(View.VISIBLE);
		setButton = (TextView) contentView.findViewById(R.id.calltypechose_dialog_setbutton);
		free_callButton = (Button)contentView.findViewById(R.id.calltypechose_dialog_freecallbutton);
		normal_callButtonSim1 = (Button)contentView.findViewById(R.id.calltypechose_dialog_normalcall_sim1);
		normal_callButtonSim2 = (Button)contentView.findViewById(R.id.calltypechose_dialog_normalcall_sim2);
		cancale_button = (Button)contentView.findViewById(R.id.calltypechose_dialog_canclebutton);
		cancale_button.setOnClickListener(this);
		/*String imei1 = SimUtil.getInstance().isSIM1Ready();
		String imei2 = SimUtil.getInstance().getImeiSIM2();*/
		//Toast.makeText(context, SimUtil.getInstance().isSIM1Ready() + " + " + SimUtil.getInstance().isSIM2Ready(), 0).show();
		if(SimUtil.getInstance().isSIM1Ready()){
			normal_callButtonSim2.setVisibility(View.VISIBLE);
			normal_callButtonSim1.setClickable(true);
			normal_callButtonSim1.setOnClickListener(this);
			normal_callButtonSim1.setBackgroundResource(R.drawable.normal_call_button_bg);
			String sim1Name = SimUtil.getInstance().getOperatorBySlot(0);
			if(sim1Name != null && !sim1Name.equals("")){
				normal_callButtonSim1.setText(sim1Name);
			}
		}else{
			normal_callButtonSim1.setVisibility(View.GONE);
			normal_callButtonSim1.setClickable(true);
		}
		if(SimUtil.getInstance().isSIM2Ready()){
			normal_callButtonSim2.setVisibility(View.VISIBLE);
			normal_callButtonSim2.setClickable(true);
			normal_callButtonSim2.setOnClickListener(this);
			normal_callButtonSim2.setBackgroundResource(R.drawable.normal_call_button_bg);
		}else{
			normal_callButtonSim2.setVisibility(View.GONE);
			normal_callButtonSim2.setClickable(true);
			String sim2Name = SimUtil.getInstance().getOperatorBySlot(1);
			if(sim2Name != null && !sim2Name.equals("")){
				normal_callButtonSim1.setText(sim2Name);
			}
		}
		if(SimUtil.getInstance().isSIM1Ready() && SimUtil.getInstance().isSIM2Ready()){
			normal_callButtonSim1.append("(ø®1)");
			normal_callButtonSim2.append("(ø®2)");
		}else{
			rootView.setVisibility(View.GONE);
		}
		screenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		screenHeight = ScreenUtil.getScreenUtil(context).getScreenHeight(); 
		setButton.setOnClickListener(this);
		free_callButton.setOnClickListener(this);
		//normal_callButtonSim2.setOnClickListener(this);
		/*lp.x = 50;
		lp.y = screenHeight/3;*/
		/*	dialogWindow.setAttributes(lp);
		callTypeChoseDialog.getWindow().setAttributes(lp);*/

		callTypeChoseDialog.setContentView(contentView, new LinearLayout.LayoutParams(  
				screenWidth,
				LayoutParams.WRAP_CONTENT));
	};

	public void showDialog(String number, String callName, String simType){
		this.callNumber = number;
		this.callName = callName;
		this.simType = simType;
		if(!isShow){
			callTypeChoseDialog.show();
			isShow = true;
		}
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
		case R.id.calltypechose_dialog_setbutton:
			Log.e("c", "…Ë÷√");
			break;

		case R.id.calltypechose_dialog_freecallbutton:
			/*if(!UserLoginUtil.getInstance(context).isUserIsLogin()){
				Toast.makeText(context, "Œ¥µ«¬º", 0).show();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context, LoginActivity.class);
						context.startActivity(intent);
					}
				}, 1000);
				return;
			}*/
			Intent callIntent = new Intent(this.context, IsCallIngActivity.class);
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("number", this.callNumber);
				if(this.callName != null)
				{
					jsonObject.put("name", this.callName);
				}else{
					jsonObject.put("name", "");
				}
				jsonObject.put("callType", 0);
				jsonObject.put("simType", this.simType);
				callIntent.putExtra("callData", jsonObject.toString());
				this.context.startActivity(callIntent);
				closeDialog();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.calltypechose_dialog_normalcall_sim1:
			SimUtil.getInstance().call(context, 0, callNumber);
			/*Intent intentSim1 = new Intent();  
			intentSim1.setAction(Intent.ACTION_CALL);  
			intentSim1.setData(Uri.parse("tel:" + callNumber));  
			this.context.startActivity(intentSim1);  */
			break;
		case R.id.calltypechose_dialog_normalcall_sim2:
			/*Intent intentSim2 = new Intent();  
			intentSim2.setAction(Intent.ACTION_CALL);  
			intentSim2.setData(Uri.parse("tel:" + callNumber));  
			this.context.startActivity(intentSim2);  */
			SimUtil.getInstance().call(context, 1, callNumber);
			break;
		case R.id.calltypechose_dialog_canclebutton:
			/*Intent intentSim2 = new Intent();  
			intentSim2.setAction(Intent.ACTION_CALL);  
			intentSim2.setData(Uri.parse("tel:" + callNumber));  
			this.context.startActivity(intentSim2);  */
			this.closeDialog();
			break;
		}
	}

}

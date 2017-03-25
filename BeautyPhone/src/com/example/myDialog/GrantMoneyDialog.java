package com.example.myDialog;

import com.example.activity.UserLoginUtil;
import com.example.beautyphone.R;
import com.example.beautyphone.UrlManager;
import com.example.beautyphone.network.HtmlVideoBean;
import com.example.beautyphone.network.HttpUtil;
import com.example.beautyphone.network.Bean.GrantMoneyReponseBean;
import com.example.beautyphone.network.Bean.GrantMoneyRequestBean;
import com.example.beautyphone.network.Bean.UserLoginResponse;
import com.example.myDialog.TipDialog.tipDialotButtonListener;
import com.example.util.BitmapUtil;
import com.example.util.NetWorkUtil;
import com.example.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.skymobi.pay.tlv.TLVCodeUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
public class GrantMoneyDialog implements OnClickListener{
	public boolean isShow = false;
	private Context context = null;
	private float screenWidth = 0;
	private float screenHeight = 0;
	private Dialog callTypeChoseDialog = null;
	private RelativeLayout rootView = null;
	private TextView editMoney = null;
	private EditText money = null;
	private TextView userName = null;
	private ImageView userImage = null;
	private ImageView close = null;
	private Button payButton = null;
	private HtmlVideoBean videoBean = null;
	private TipDialog tipDialog = null;

	private void initView(View contentView) {

		// TODO Auto-generated method stub
		rootView = (RelativeLayout)contentView.findViewById(R.id.grant_money_rootview);
		editMoney = (TextView)contentView.findViewById(R.id.grant_money_editmoney);
		money = (EditText)contentView.findViewById(R.id.grant_money_money);
		userImage = (ImageView)contentView.findViewById(R.id.grant_money_userconver);
		close = (ImageView)contentView.findViewById(R.id.grant_money_close);
		userName = (TextView)contentView.findViewById(R.id.grant_money_username);
		payButton = (Button)contentView.findViewById(R.id.grant_money_payMoney);
		payButton.setOnClickListener(this);
		editMoney.setOnClickListener(this);
		close.setOnClickListener(this);
	}

	public GrantMoneyDialog(Context context){
		this.context = context;
		tipDialog = new TipDialog(context);
		callTypeChoseDialog = new Dialog(context, R.style.grant_money_dialogstyle);
		callTypeChoseDialog.setCancelable(false);
		callTypeChoseDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				isShow = false;
			}
		});
		//dialogWindow.setGravity(Gravity.BOTTOM);
		View contentView = LinearLayout.inflate(context, R.layout.grant_money_bg, null);
		initView(contentView);
		screenWidth = ScreenUtil.getScreenUtil(context).getScreenWidth();
		screenHeight = ScreenUtil.getScreenUtil(context).getScreenHeight();
		callTypeChoseDialog.setContentView(contentView, new LinearLayout.LayoutParams(  
				(int) screenWidth,
				(int) screenHeight));
	};


	public void showDialog(HtmlVideoBean htmlVideoBean){
		if(!isShow){
			this.videoBean = htmlVideoBean;
			setmage_size();
			ImageLoader.getInstance().loadImage(videoBean.getUserImageUri(), options, new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					// TODO Auto-generated method stub
					Bitmap bitmap = BitmapUtil.toRoundBitmap(arg2);
					userImage.setImageBitmap(bitmap);
				}

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub

				}
			});
			//ImageLoader.getInstance().displayImage(videoBean.getUserImageUri(), videoholder.videoUserConver, options);
			userName.setText(videoBean.getUserName());
			float moneys = (float) (1 * Math.random());
			money.setText((moneys + "").substring(0, 4));
			callTypeChoseDialog.show();
			isShow = true;
		}
	}
	//显示图片的配置  
	DisplayImageOptions options = new DisplayImageOptions.Builder()  
			.showImageOnLoading(R.drawable.test_cover)  
			.showImageOnFail(R.drawable.test_cover)  
			.cacheInMemory(true)  
			.cacheOnDisk(true)
			.bitmapConfig(Bitmap.Config.RGB_565)  
			.build(); 

	private void setmage_size() {
		float width = (float) (screenWidth * 0.65);
		float height = (float) (width * 1.141);
		android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) rootView.getLayoutParams();
		layoutParams.height = (int) height;
		layoutParams.width = (int) width;
		rootView.setLayoutParams(layoutParams);
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
		case R.id.grant_money_close:
			closeDialog();
			break;

		case R.id.grant_money_payMoney:
			grant();
			break;
		case R.id.grant_money_editmoney:
			float moneys = (float) (1 * Math.random());
			money.setText((moneys + "").substring(0, 4));
			break;
		}
	}

	private void grant() {
		// TODO Auto-generated method stub
		final GrantMoneyRequestBean grantMoney = new GrantMoneyRequestBean();
		String moneys = "";
		String mo = "";
		if(money.getText() != null && !money.getText().toString().isEmpty()){
			moneys = money.getText().toString();
			if(moneys.equals(".")){
				Toast.makeText(context, "金额格式不正确", 0).show();
				return;
			}
			float moneysize = Float.parseFloat(moneys);
			if(moneysize < 0.01){
				Toast.makeText(context, "至少0.01元哦", 0).show();
				return;
			}
			grantMoney.setGrantUserMoney(Float.parseFloat(moneys) + "");
		}else{
			Toast.makeText(context, "金额不能为空", 0).show();
			return;
		}
		UserLoginResponse loginResponse = UserLoginUtil.getInstance(context).getLoginUserInfo();
		if(loginResponse == null){
			Toast.makeText(context, "还未登录", 0).show();
			return;
		}
		grantMoney.setVideoId(videoBean.getData_id());
		grantMoney.setVideoTitle(videoBean.getVideoTitle());
		grantMoney.setGrantUserAccount(loginResponse.getUserAccount());
		grantMoney.setGrantUser(loginResponse.getUserAccount());
		tipDialog.showDialog("确认打赏“" + videoBean.getUserName() + "”" + grantMoney.getGrantUserMoney() + "元?", new tipDialotButtonListener() {

			@Override
			public void click(boolean isOk) {
				// TODO Auto-generated method stub
				if(isOk){
					if(NetWorkUtil.checkNetworkAvailable(context)){
						HttpUtil.getInstance().okHttpDoPost(UrlManager.GRANT_VIDEO_MONEY, grantMoney, grantHandler);
					}else{
						Toast.makeText(context, "网络未连接", 0).show();
					}
				}
			}
		});
	}


	private Handler grantHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg != null){
				byte[] data = msg.getData().getByteArray("byte");
				GrantMoneyReponseBean bean = TLVCodeUtil.decode(data, GrantMoneyReponseBean.class);
				if(bean != null){
					if(bean.getGrantState() != null && bean.getGrantState().equals("1")){
						UserLoginResponse loginResponse = UserLoginUtil.getInstance(context).getLoginUserInfo();
						loginResponse.setBalanceMoney(bean.getBlackMoney());
						UserLoginUtil.getInstance(context).saveLoginData(loginResponse);
						Toast.makeText(context, "打赏成功", 0).show();
						Log.e("c", bean.toString());
					}else{
						Toast.makeText(context, "超过可用余额", 0).show();
					}
				}else {
					Log.e("c", "返回数据为空");
				}
			}
			closeDialog();
		};
	};
}

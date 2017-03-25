package com.example.activity;

import java.util.ArrayList;

import com.example.adapter.MyInfomationViewPagerAdapter;
import com.example.beautyphone.R;
import com.example.contacts.ContactChangeListener;
import com.example.db.ContactsUtil;
import com.example.fragment.ContactInfomationCallLogFragment;
import com.example.fragment.ContactInfomationCallSoundFragment;
import com.example.fragment.ContactInfomationInfomationFragment;
import com.example.util.ScreenUtil;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsInfomationActivity extends FragmentActivity implements OnClickListener,OnPageChangeListener{
	private ViewPager viewPager = null;
	private ArrayList<Fragment> infomationFragmentList = null;
	private LinearLayout nameLinearLayout = null;
	private ImageView favoriteEdit = null;
	private ImageView imfomationEdit = null;
	private ImageView otherMenuEdit = null;
	private TextView nameText = null;
	private TextView scrolBar = null;
	private boolean viewPageScrolled = true;
	private LayoutParams scroolLayoutparams = null;
	private int screenWidht = 0;
	private TextView allCallLogTextView = null;
	private TextView infomationTextView = null;
	private TextView saveSoundTextView = null;

	private ContactInfomationCallLogFragment callLogFragment = null;
	private ContactInfomationInfomationFragment infomationFragment = null;
	private ContactInfomationCallSoundFragment soundLogFragment = null;
	private String fromData = "";
	private String fromType = "";
	private String streedType = "";
	private int numberType = -1;

	private ContactChangeBroadCase contactChangeBroadCase = null;
	private CallLogChangeBroadCase calllogChangeBroadCase = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_infomationactivity_layout);
		initView();
		setInfomationFragment();
	}



	private void initView() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		fromData = intent.getStringExtra("data1");
		fromType = intent.getStringExtra("intentType");
		if(fromType.equals("calllogItem")){
			numberType = intent.getIntExtra("data2", -1);
		}
		Log.e("info", fromType + " + " + fromData + " + " + numberType);
		//Toast.makeText(ContactsInfomationActivity.this, calllogItem + "", duration).show();
		screenWidht = ScreenUtil.getScreenUtil(ContactsInfomationActivity.this).getScreenWidth();
		viewPager = (ViewPager)this.findViewById(R.id.contactsinfomation__viewpager_containt);
		nameLinearLayout = (LinearLayout)this.findViewById(R.id.contactsinfomation_namelayout);
		favoriteEdit = (ImageView)this.findViewById(R.id.contactsinfomation_sterredstar);
		imfomationEdit = (ImageView)this.findViewById(R.id.contactsinfomation_edit);
		otherMenuEdit = (ImageView)this.findViewById(R.id.contactsinfomation_othermenu);
		nameText = (TextView)this.findViewById(R.id.contactsinfomation_nametext);
		allCallLogTextView = (TextView)this.findViewById(R.id.contactsinfomation_allcalllog);
		infomationTextView = (TextView)this.findViewById(R.id.contactsinfomation_infomation);
		saveSoundTextView = (TextView)this.findViewById(R.id.contactsinfomation_calllogsound);
		scrolBar = (TextView)this.findViewById(R.id.contactsinfomation_viewpagerscrolbar);
		LayoutParams layoutParams = (LayoutParams) scrolBar.getLayoutParams();
		layoutParams.width = screenWidht/3;
		scrolBar.setLayoutParams(layoutParams);
		viewPager.setOnPageChangeListener(this);
		nameLinearLayout.setOnClickListener(this);
		favoriteEdit.setOnClickListener(this);
		imfomationEdit.setOnClickListener(this);
		otherMenuEdit.setOnClickListener(this);

	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(ContactsUtil.infomationBean != null && ContactsUtil.infomationBean.getContact_id() != null
					&& !ContactsUtil.infomationBean.getContact_id().equals("")){
				favoriteEdit.setVisibility(View.VISIBLE);
				streedType = ContactsUtil.getContactToFavoriteType(ContactsInfomationActivity.this, ContactsUtil.infomationBean.getContact_id());
				if(streedType != null && streedType.equals("1")){
					favoriteEdit.setImageResource(R.drawable.up_toolbar_star_added);
				}else if(streedType != null && streedType.equals("0")){
					favoriteEdit.setImageResource(R.drawable.up_toolbar_star);
				}
			}else{
				favoriteEdit.setVisibility(View.GONE);
			}
			if(ContactsUtil.infomationBean.getName()!= null && !ContactsUtil.infomationBean.getName().equals("")){
				nameText.setText(ContactsUtil.infomationBean.getName());
			}else{
				if(ContactsUtil.infomationBean.getNumberList() != null && ContactsUtil.infomationBean.getNumberList().size() > 0){
					nameText.setText(ContactsUtil.infomationBean.getNumberList().get(0).getNumber());
				}
			}
			if(ContactsUtil.infomationBean.getNumberList() != null &&
					ContactsUtil.infomationBean.getNumberList().size() > 0){
				callLogFragment.initRecycleView(ContactsUtil.infomationBean.getNumberList().get(0).getNumber());
			}
			infomationFragment.updataData(ContactsUtil.infomationBean);
		}
	};

	public void setInfomationFragment() {
		// TODO Auto-generated method stub
		infomationFragmentList = new ArrayList<Fragment>();
		callLogFragment = new ContactInfomationCallLogFragment();
		infomationFragment = new ContactInfomationInfomationFragment();
		soundLogFragment = new ContactInfomationCallSoundFragment();
		infomationFragmentList.add(callLogFragment);
		infomationFragmentList.add(infomationFragment);
		infomationFragmentList.add(soundLogFragment);
		viewPager.setOffscreenPageLimit(2);
		Log.e("c", "输出了2");
		MyInfomationViewPagerAdapter myViewPagerAdapter = new MyInfomationViewPagerAdapter(getSupportFragmentManager(), infomationFragmentList);
		Log.e("c", "输出了3");
		viewPager.setAdapter(myViewPagerAdapter);

		if(fromType != null && fromType.equals("contactMainItem")){
			viewPager.setCurrentItem(1);
		}
		calllogChangeBroadCase = new CallLogChangeBroadCase(ContactsInfomationActivity.this, contactChangeHandler);
		ContactsInfomationActivity.this.getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI,
				true, calllogChangeBroadCase);
		beginToGetInfo();
	}



	private void beginToGetInfo() {
		if(fromData != null && !fromData.equals("") && !fromType.equals("")){	//开启请求联系人详细数据
			if(fromType.equals("contactMainItem")){
				Log.e("获取详情", "by:contactMainItem");
				ContactsUtil.getContactInfoByCountIdONumber(0, fromData, null, handler);
			}else if(fromType.equals("calllogItem")){
				//callLogFragment.initRecycleView(fromData);
				Log.e("获取详情", "by:calllogItem");
				ContactsUtil.getContactInfoByCountIdONumber(1, null, fromData, handler);
			}
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contactsinfomation_namelayout:
			finish();
			break;
		case R.id.contactsinfomation_sterredstar:
			if(ContactsUtil.infomationBean != null && ContactsUtil.infomationBean.getContact_id() != null
			&& !ContactsUtil.infomationBean.getContact_id().equals("")){
				if(streedType != null && streedType.equals("0")){
					int updataState = ContactsUtil.setContactToFavorite(ContactsInfomationActivity.this,
							1 + "", ContactsUtil.infomationBean.getContact_id());
					if(updataState != -1){
						streedType = 1 + "";
						favoriteEdit.setImageResource(R.drawable.up_toolbar_star_added);
					}
				}else if(streedType != null && streedType.equals("1")){
					int updataState = ContactsUtil.setContactToFavorite(ContactsInfomationActivity.this, 
							0 + "", ContactsUtil.infomationBean.getContact_id());
					if(updataState != -1){
						streedType = 0 + "";
						favoriteEdit.setImageResource(R.drawable.up_toolbar_star);
					}

				}
			}
			break;
		case R.id.contactsinfomation_edit:
			editContact();
			break;
		case R.id.contactsinfomation_othermenu:

			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(calllogChangeBroadCase != null){
			this.getContentResolver().unregisterContentObserver(calllogChangeBroadCase);
		}
		if(contactChangeBroadCase != null){
			this.getContentResolver().unregisterContentObserver(contactChangeBroadCase);
		}
	}


	private void editContact() {
		// TODO Auto-generated method stub
		contactChangeBroadCase = new ContactChangeBroadCase(ContactsInfomationActivity.this, contactChangeHandler);
		this.getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, contactChangeBroadCase);
		if(ContactsUtil.infomationBean != null && !ContactsUtil.infomationBean.getContact_id().isEmpty()){
			Intent editIntent = new Intent(Intent.ACTION_EDIT,Uri.parse("content://com.android.contacts/contacts/"+fromData));
			startActivity(editIntent);
		}
	}


	public class ContactChangeBroadCase extends ContentObserver{
		private static final String TAG = "ContactChangeBroadCase";
		private Context context = null;
		private Handler handler = null;
		public ContactChangeBroadCase(Context context,Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.handler = handler;
		}
		@Override
		public void onChange(boolean selfChange, Uri uri) {
			// TODO Auto-generated method stub
			super.onChange(selfChange, uri);
			Log.e(TAG, uri.toString());
			beginToGetInfo();
		}

	}

	public class CallLogChangeBroadCase extends ContentObserver{
		private static final String TAG = "CallLogChangeBroadCase";
		private Context context = null;
		private Handler handler = null;
		public CallLogChangeBroadCase(Context context,Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.handler = handler;
		}
		@Override
		public void onChange(boolean selfChange, Uri uri) {
			// TODO Auto-generated method stub
			super.onChange(selfChange, uri);
			Log.e(TAG, uri.toString());
			if(callLogFragment != null){
				if(ContactsUtil.infomationBean.getNumberList() != null &&
						ContactsUtil.infomationBean.getNumberList().size() > 0){
					callLogFragment.initRecycleView(ContactsUtil.infomationBean.getNumberList().get(0).getNumber());
					//	callLogFragment.initRecycleView(ContactsUtil.infomationBean.getNumberList().get(0).getNumber());
				}

			}
		}

	}

	private Handler contactChangeHandler = new Handler(){
		public void handleMessage(Message msg) {
			//Log.e("监听变化", msg.obj.toString());
		};
	};

	private Handler calllogChangeHandler = new Handler(){
		public void handleMessage(Message msg) {
			//Log.e("监听变化", msg.obj.toString());
		};
	};


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		if(viewPageScrolled){
			viewPageScrolled = false;
			scroolLayoutparams = (LayoutParams) scrolBar.getLayoutParams();
		}
		scroolLayoutparams.leftMargin = (int) (screenWidht/3*arg1) + screenWidht/3 * arg0;
		Log.e("c", arg0 + " + " + arg1 + " + " + arg2);
		scrolBar.setLayoutParams(scroolLayoutparams);
	}


	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			allCallLogTextView.setTextColor(Color.rgb(255, 255, 255));
			infomationTextView.setTextColor(Color.rgb(206, 206, 206));
			saveSoundTextView.setTextColor(Color.rgb(206, 206, 206));
			break;
		case 1:
			allCallLogTextView.setTextColor(Color.rgb(206, 206, 206));
			infomationTextView.setTextColor(Color.rgb(255, 255, 255));
			saveSoundTextView.setTextColor(Color.rgb(206, 206, 206));
			break;
		case 2:
			allCallLogTextView.setTextColor(Color.rgb(206, 206, 206));
			infomationTextView.setTextColor(Color.rgb(206, 206, 206));
			saveSoundTextView.setTextColor(Color.rgb(255, 255, 255));
			break;
		}
	}
}

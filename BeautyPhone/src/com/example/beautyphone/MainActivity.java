package com.example.beautyphone;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MyViewPagerAdapter;
import com.example.contacts.CallSearchResultBean;
import com.example.contacts.CardLocationBean;
import com.example.fragment.CallListFragment;
import com.example.fragment.CallListFragment.numberboardListener;
import com.example.fragment.ContactsFragment;
import com.example.fragment.PersonalFragment;
import com.example.myDialog.CallTypeChoseDialog;
import com.example.util.NumberLocationUtil;
import com.example.util.ScreenUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener,OnPageChangeListener,IUiListener{
	private LinearLayout number_show_containerlayout = null;
	private EditText numberTextView = null;
	private TextView numberDescribe = null;
	private String inputedNumber = "";
	private CallSearchResultBean searchBean = null;
	private LinearLayout container = null;
	private LinearLayout callList = null;
	private LinearLayout message = null;
	private TextView scrolBarView = null;
	private ViewPager viewPager = null;
	private List<Fragment> fragment_viewpager_list = null;
	private int screenWidht = 0;
	private LayoutParams scroolLayoutparams = null;
	private boolean scrolled = true;
	private ContactsFragment contactsFragment = null;
	private CallListFragment callListFragment = null;
	private PersonalFragment messageFragment = null;
	private CardLocationBean cardBean = null;		//存储电话号码归属地的变量
	private CallTypeChoseDialog callTypeChoseDialog = null;
	private Tencent mTencent = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setfragmentData();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null != mTencent) 
			mTencent.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		JCVideoPlayer.releaseAllVideos();
	}

	public void initView() {
		mTencent = Tencent.createInstance("1105092074", MainActivity.this);
		screenWidht = ScreenUtil.getScreenUtil(MainActivity.this).getScreenWidth();
		container = (LinearLayout)this.findViewById(R.id.mainactivitylayout_contact_layout);
		callList = (LinearLayout)this.findViewById(R.id.mainactivitylayout_callog_layout);
		message = (LinearLayout)this.findViewById(R.id.mainactivitylayout_message_layout);
		viewPager = (ViewPager)this.findViewById(R.id.mainactivitylayout_viewpager_containt);
		scrolBarView = (TextView)this.findViewById(R.id.scrolbarlayout);
		number_show_containerlayout = (LinearLayout)this.findViewById(R.id.number_show_number_containerlayout);
		numberTextView = (EditText)this.findViewById(R.id.number_show_number);
		numberDescribe = (TextView)this.findViewById(R.id.number_show_number_describe);
		number_show_containerlayout.setOnClickListener(this);
		callTypeChoseDialog = new CallTypeChoseDialog(MainActivity.this);
		LayoutParams layoutParams = (LayoutParams) scrolBarView.getLayoutParams();
		layoutParams.width = screenWidht/3;
		scrolBarView.setLayoutParams(layoutParams);
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			numberTextView.setInputType(InputType.TYPE_NULL);
		} else {
			MainActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setSoftInputShownOnFocus;
				setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setSoftInputShownOnFocus.setAccessible(true);
				setSoftInputShownOnFocus.invoke(numberTextView, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		container.setOnClickListener(this);
		callList.setOnClickListener(this);
		message.setOnClickListener(this);
		numberTextView.addTextChangedListener(textWatcher);
		//	setDefaultFragment();
		viewPager.setOnPageChangeListener(this);
	}

	public void setfragmentData() {
		// TODO Auto-generated method stub
		fragment_viewpager_list = new ArrayList<Fragment>();
		//FragmentManager fm = getSupportFragmentManager();

		contactsFragment = new ContactsFragment();
		callListFragment = new CallListFragment();
		messageFragment = new PersonalFragment();
		callListFragment.setonNumberBoardListener(new numberboardListener() {

			@Override
			public void clickType(String numberChar, int doType, CallSearchResultBean bean) {
				// TODO Auto-generated method stub
				searchBean = bean;
				showNumberLayout(numberChar, doType);
			}
		});
		fragment_viewpager_list.add(callListFragment);
		fragment_viewpager_list.add(contactsFragment);
		fragment_viewpager_list.add(messageFragment);
		viewPager.setOffscreenPageLimit(2);
		Log.e("c", "输出了2");
		MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragment_viewpager_list);
		Log.e("c", "输出了3");
		viewPager.setAdapter(myViewPagerAdapter);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		/*FragmentManager fm = that.getSupportFragmentManager();  
		FragmentTransaction transaction = fm.beginTransaction();  */
		//transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		switch (arg0.getId()) {
		case R.id.mainactivitylayout_callog_layout:
			//Toast.makeText(MainActivity.this, "联系人", 0).show();
			viewPager.setCurrentItem(0, true);
			break;
		case R.id.mainactivitylayout_contact_layout:
			//Toast.makeText(MainActivity.this, "通话记录", 0).show();
			viewPager.setCurrentItem(1, true);
			break;
		case R.id.mainactivitylayout_message_layout:
			//	Toast.makeText(MainActivity.this, "消息", 0).show();
			viewPager.setCurrentItem(2, true);
			break;
		case R.id.number_show_number_containerlayout:
			callListFragment.showNumberBoard();
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		callListFragment.closeNumberBoard();
		closeNumberShowView();
		//number_show_containerlayout.setVisibility(View.GONE);
		// TODO Auto-generated method stub
		/*LayoutParams layoutParams = (LayoutParams) scrolBarView.getLayoutParams();
		layoutParams.width = screenWidht/3;
		scrolBarView.setLayoutParams(layoutParams);*/
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

		if(scrolled){
			scrolled = false;
			scroolLayoutparams = (LayoutParams) scrolBarView.getLayoutParams();
		}
		scroolLayoutparams.leftMargin = (int) (screenWidht/3*arg1) + screenWidht/3 * arg0;
		Log.e("c", arg0 + " + " + arg1 + " + " + arg2);
		scrolBarView.setLayoutParams(scroolLayoutparams);
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	private void showNumberLayout(String numberChar, int doType) {
		// TODO Auto-generated method stub
		if(!inputedNumber.equals("")){		//显示动画
			//number_show_containerlayout.setVisibility(View.VISIBLE);
			showNumberShowView();
		}
		Log.e("c", "拨号盘点击了, numberChar:" + numberChar + " + doType:" + doType );
		if(doType == 1){		//长按
			switch (numberChar) {
			case "delete":
				if(!inputedNumber.equals("") && number_show_containerlayout.getVisibility() == View.VISIBLE){
					inputedNumber = "";
					//number_show_containerlayout.setVisibility(View.GONE);
					setNumberEditTextText(0);
					closeNumberShowView();
				}
				break;
			default:
				break;
			}
		}else if(doType == 0){
			switch (numberChar) {
			case "itemclick":
				if(searchBean != null){
					Log.e("c", searchBean.getNumber() + "搜索的电话号码");
					numberTextView.setFocusable(true);
					numberTextView.requestFocus();
					showNumberShowView();
					ArrayList<String> numberList = new ArrayList<String>();
					numberList = searchBean.getNumberList();
					if(numberList != null && numberList.size() > 0){	//进行电话号码的适配
						inputedNumber = searchBean.getNumberList().get(0);
						for (String s:numberList){
							if(s.indexOf(searchBean.getSerchNumber()) >= 0){
								inputedNumber = s;
								break;
							}
						}
						if(inputedNumber != null && inputedNumber.length() > 0){
							setNumberEditTextText(inputedNumber.length());
							getNumberLocations(inputedNumber);
							//numberDescribe.setText("未知归属地");
						}
					}
				}
				break;
			case "0":
				inputedNumberMethod("0");
				break;
			case "1":
				inputedNumberMethod("1");
				break;
			case "2":
				inputedNumberMethod("2");
				break;
			case "3":
				inputedNumberMethod("3");
				break;
			case "4":
				inputedNumberMethod("4");
				break;
			case "5":
				inputedNumberMethod("5");
				break;
			case "6":
				inputedNumberMethod("6");
				break;
			case "7":
				inputedNumberMethod("7");
				break;
			case "8":
				inputedNumberMethod("8");
				break;
			case "9":
				inputedNumberMethod("9");
				break;
			case "*":
				//number_show_containerlayout.setVisibility(View.VISIBLE);
				inputedNumberMethod("*");
				break;
			case "#":
				//number_show_containerlayout.setVisibility(View.VISIBLE);
				inputedNumberMethod("#");
				break;
			case "close":
				/*inputedNumber = inputedNumber + "0";
				numberTextView.setText(inputedNumber);*/
				break;
			case "call":
				String name = "";
				String card = "";
				if(searchBean != null){
					name = searchBean.getName();
				}
				if(cardBean != null){
					card = cardBean.getProvince() + cardBean.getCity() + " " +cardBean.getSimType();
				}
				callTypeChoseDialog.showDialog(inputedNumber, name, card);
				break;
			case "delete":
				Log.e("c", "delete");
				numberTextView.setFocusable(true);
				numberTextView.requestFocus();
				int selectionIndex = numberTextView.getSelectionStart();
				if(selectionIndex > 0){
					inputedNumber = inputedNumber.substring(0, selectionIndex - 1) + inputedNumber.substring(selectionIndex, inputedNumber.length());
					Log.e("c", "删除后的inputnumber + " + inputedNumber);
					if(selectionIndex - 1 > 0){
						setNumberEditTextText(selectionIndex - 1);
					}else{
						setNumberEditTextText(0);
					}
				}
				if(inputedNumber.length() <= 0){
					setNumberEditTextText(0);
					closeNumberShowView();
				}
				/*
				if(inputedNumber.length() > 0)
					inputedNumber = inputedNumber.substring(0, inputedNumber.length() - 1);
				if(inputedNumber.length() <= 0){
					//number_show_containerlayout.setVisibility(View.GONE);
					closeNumberShowView();
				}else{
					setNumberEditTextText();
				}*/
				break;
			}
		}
	}

	private void setNumberEditTextText(int i) {
		numberTextView.setText(inputedNumber);
		numberTextView.setSelection(i);
		Log.e("c", numberTextView.getSelectionStart() + " 光标位置");
	}

	private void inputedNumberMethod(String ch) {
		//number_show_containerlayout.setVisibility(View.VISIBLE);
		numberTextView.setFocusable(true);
		numberTextView.requestFocus();
		int selection = numberTextView.getSelectionStart();
		showNumberShowView();

		String str1 = inputedNumber.substring(0, selection);
		String str2 = inputedNumber.substring(selection, inputedNumber.length());
		inputedNumber = str1 + ch + str2;
		setNumberEditTextText(selection + 1);
		getNumberLocations(inputedNumber);
		//numberDescribe.setText("未知归属地");

		/*if(inputedNumber.length() < 20){
			inputedNumber = inputedNumber + ch;*/
		//}
		//if(inputedNumber.length() == 7){
		//}else if(inputedNumber.length() < 7){
		//}
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Bundle bundle = msg.getData();
			if(bundle != null){
				cardBean = (CardLocationBean)bundle.getSerializable("simBean");
				if(cardBean != null){
					numberDescribe.setText(cardBean.getProvince() + cardBean.getCity() + " " + cardBean.getSimType());
				}else{
					numberDescribe.setText("未知归属地");
				}
			}else{
				numberDescribe.setText("未知归属地");
			}

			//Toast.makeText(getApplicationContext(), cardBean.toString(), Toast.LENGTH_LONG).show();
		}
	};
	private void getNumberLocations(String number) {
		// TODO Auto-generated method stub
		if(cardBean != null && cardBean.getNumber().equals(number)){
			numberDescribe.setText(cardBean.getProvince() + " " + cardBean.getCity() + " " + cardBean.getSimType());
		}else{
			NumberLocationUtil locationUtil = new NumberLocationUtil();
			locationUtil.getNumberLocation(number, handler);
		}
	}

	public void showNumberShowView(){
		if(number_show_containerlayout.getVisibility() == View.GONE){
			startNumberShowViewOpenAnim(number_show_containerlayout);
		}
	}

	public void closeNumberShowView(){
		Log.e("c", "关闭");
		if(number_show_containerlayout.getVisibility() == View.VISIBLE){
			Log.e("c", "可见");
			callListFragment.showDefaultListData();
			startNumberShowViewCloseAnim(number_show_containerlayout);
		}else{
			Log.e("c", "不可见");
		}
	}


	/**
	 * 号码显示控件出现动画
	 */
	public void startNumberShowViewOpenAnim(final View v){
		if(v.getVisibility() == View.VISIBLE){
			return;
		}
		v.setVisibility(View.VISIBLE);
		v.clearAnimation();
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(300);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -30, 0);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(translateAnimation);
		v.startAnimation(animationSet);
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

			}
		});
	}
	/**
	 * 号码显示控件消失动画
	 */
	public void startNumberShowViewCloseAnim(final View v){
		if(v.getVisibility() == View.GONE){
			return;
		}
		v.clearAnimation();
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(300);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -30);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(translateAnimation);
		v.startAnimation(animationSet);
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				v.setVisibility(View.GONE);
			}
		});
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			callListFragment.updataSearchDataListData(inputedNumber);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};
	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
		Log.e("c", "qq关闭");
	}

	@Override
	public void onComplete(Object arg0) {
		// TODO Auto-generated method stub
		Log.e("c", "qq成功");
	}

	@Override
	public void onError(UiError arg0) {
		// TODO Auto-generated method stub
		Log.e("c", "qq错误");
	}
}

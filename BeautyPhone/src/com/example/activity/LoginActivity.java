package com.example.activity;

import java.util.ArrayList;

import com.example.adapter.LoginViewPageAdpater;
import com.example.beautyphone.R;
import com.example.fragment.LoginFragment;
import com.example.fragment.RegistFragment;
import com.example.myDialog.LoadingDialog;
import com.example.util.ScreenUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends FragmentActivity {
	private ViewPager fragmentViewPage = null;
	private ImageView viewPageScrollbar = null;
	private TextView loginTextView = null;
	private TextView registTextView = null;
	private ArrayList<Fragment> fragmentList = null;
	private LoginViewPageAdpater viewAdapter = null;
	private int screenWidth = 0;
	private android.widget.RelativeLayout.LayoutParams scrollLayoutParams = null;
	private boolean scrolled = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);
		initView();
		initViewData();
	}

	private void initViewData() {
		// TODO Auto-generated method stub
		initViewPage();
	}

	private void initViewPage() {
		// TODO Auto-generated method stub
		fragmentList = new ArrayList<Fragment>();
		LoginFragment loginFragment = new LoginFragment();
		RegistFragment registFragment = new RegistFragment();
		fragmentList.add(loginFragment);
		fragmentList.add(registFragment);
		viewAdapter = new LoginViewPageAdpater(getSupportFragmentManager(), fragmentList);
		fragmentViewPage.setAdapter(viewAdapter);
	}

	private void initView() {
		// TODO Auto-generated method stub
		fragmentViewPage = (ViewPager)this.findViewById(R.id.loginactivity_viewpager_containt);
		viewPageScrollbar = (ImageView)this.findViewById(R.id.login_activity_viewscrollbar);
		loginTextView = (TextView)this.findViewById(R.id.loginactivity_view_title_login);
		registTextView = (TextView)this.findViewById(R.id.loginactivity_view_title_regist);
		screenWidth = ScreenUtil.getScreenUtil(LoginActivity.this).getScreenWidth();
		android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) viewPageScrollbar.getLayoutParams();
		layoutParams.width = screenWidth/2;
		viewPageScrollbar.setLayoutParams(layoutParams);
		fragmentViewPage.setOnPageChangeListener(changeListener);;
	}

	private OnPageChangeListener changeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			if(scrolled){
				scrolled = false;
				scrollLayoutParams = (android.widget.RelativeLayout.LayoutParams) viewPageScrollbar.getLayoutParams();
			}
			scrollLayoutParams.leftMargin = (int) (screenWidth/2*arg1) + screenWidth/2 * arg0;
			viewPageScrollbar.setLayoutParams(scrollLayoutParams);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};

	/*private OnScrollChangeListener changeListener = new OnScrollChangeListener() {

		@Override
		public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
			// TODO Auto-generated method stub
			if(scrolled){
				scrolled = false;
				scrollLayoutParams = (android.widget.RelativeLayout.LayoutParams) viewPageScrollbar.getLayoutParams();
			}
			scrollLayoutParams.leftMargin = (int) (screenWidth/2*arg1) + screenWidth/2 * arg0;
			scrolBarView.setLayoutParams(scroolLayoutparams);
		}
	};*/
}

package com.example.activity;

import com.alibaba.fastjson.JSONObject;
import com.example.beautyphone.R;
import com.example.beautyphone.news.bean.JuHeNewsJsonData;
import com.example.beautyphone.news.bean.JuHeNewsJsonItemBean;
import com.example.beautyphone.news.bean.JuHeNewsRecycleItemBean;
import com.example.beautyphone.news.bean.NewsJsonItemBean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class NewsContentShowActivity extends Activity implements OnClickListener,OnTouchListener{
	//private NewsJsonItemBean newData = null;
	private JuHeNewsJsonItemBean JuHenewData = null;
	private WebView webView = null;
	private ImageView backButton = null;
	private ImageView load_fram1 = null;
	private ImageView load_fram2 = null;
	private ImageView load_fram3 = null;
	private ImageView load_fram4 = null;
	private ImageView load_fram5 = null;
	private RelativeLayout loadRootView = null;
	private boolean isBlockImageLoad = false;
	private ProgressBar webViewLoadProgres = null;
	private int progress = 0;
	private Handler loadHandle = null;
	private int startPositionX = 0;
	private int startPositionY = 0;
	private int distanceX = 0;
	private int distanceY = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_content_show_layout);
		initView();
		initViewData();
	}
	private void initViewData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		String json = intent.getStringExtra("webJson");
		Log.e("ccc", json);
		if(!json.isEmpty()){
			loadHandle = new Handler();
			JSONObject jsonObject = JSONObject.parseObject(json);
			if(jsonObject != null){
				JuHenewData = JSONObject.toJavaObject(jsonObject, JuHeNewsJsonItemBean.class);
				if(JuHenewData != null){
					if(!JuHenewData.getUrl().isEmpty()){
						webView.getSettings().setJavaScriptEnabled(false);
						webView.getSettings().setRenderPriority(RenderPriority.HIGH);
						webView.getSettings().setBlockNetworkImage(true);
						webView.getSettings().setDomStorageEnabled(true);
						webView.getSettings().setAppCacheEnabled(true);
						webView.getSettings().setDatabaseEnabled(true);
						webView.getSettings().setAllowFileAccess(true);
						webView.setOnTouchListener(this);
						/*if (UtilTools.isConnected()) {
							webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);// 根据cache-control决定是否从网络上取数据
						} else {
							webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 先查找缓存，没有的情况下从网络获取。
						}*/
						isBlockImageLoad = true;
						webViewLoadProgres.setProgress(0);
						webViewLoadProgres.setMax(100);
						webView.setWebChromeClient(new WebChromeClient(){

							@Override
							public void onProgressChanged(WebView view, int newProgress) {
								// TODO Auto-generated method stub
								super.onProgressChanged(view, newProgress);
								if(newProgress < 100){
									webViewLoadProgres.setProgress(newProgress);
								}else{
									webViewLoadProgres.setVisibility(View.GONE);
									stopLoadIng();
									if(isBlockImageLoad){
										view.getSettings().setBlockNetworkImage(false);
										isBlockImageLoad = false;
									}
								}
							}

							@Override
							public void onReceivedIcon(WebView view, Bitmap icon) {
								// TODO Auto-generated method stub
								//	super.onReceivedIcon(view, icon);
							}

							@Override
							public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
								// TODO Auto-generated method stub
								//super.onReceivedTouchIconUrl(view, url, precomposed);
							}

						});

						/*webView.setWebViewClient(new WebViewClient(){

							@Override
							public void onPageStarted(WebView view, String url, Bitmap favicon) {
								// TODO Auto-generated method stub
								super.onPageStarted(view, url, favicon);
								loadIng();
							}

							@Override
							public void onPageFinished(WebView view, String url) {
								// TODO Auto-generated method stub
								super.onPageFinished(view, url);
								stopLoadIng();
							}

							@Override
							public boolean shouldOverrideUrlLoading(WebView view, String url) {
								// TODO Auto-generated method stub
								view.loadUrl(url);
								return super.shouldOverrideUrlLoading(view, url);
							}

							@Override
							public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
								// TODO Auto-generated method stub
								return super.shouldOverrideKeyEvent(view, event);
							}

						});*/
						webViewLoadProgres.setVisibility(View.VISIBLE);
						loadIng();
						Runnable runnable = new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								webView.loadUrl(JuHenewData.getUrl());
							}
						};
						loadHandle.postDelayed(runnable, 100);
					}
				}
			}
		}
	}


	private void stopLoadIng() {
		// TODO Auto-generated method stub
		loadRootView.setVisibility(View.GONE);
		load_fram1.clearAnimation();
		load_fram2.clearAnimation();
		load_fram3.clearAnimation();
		load_fram4.clearAnimation();
		load_fram5.clearAnimation();
	}

	private void loadIng() {
		// TODO Auto-generated method stub
		loadRootView.setVisibility(View.VISIBLE);
		Animation animation1 = AnimationUtils.loadAnimation(NewsContentShowActivity.this, R.anim.web_load_fram1_anim);
		Animation animation2 = AnimationUtils.loadAnimation(NewsContentShowActivity.this, R.anim.web_load_fram2_anim);
		Animation animation3 = AnimationUtils.loadAnimation(NewsContentShowActivity.this, R.anim.web_load_fram3_anim);
		Animation animation4 = AnimationUtils.loadAnimation(NewsContentShowActivity.this, R.anim.web_load_fram4_anim);
		Animation animation5 = AnimationUtils.loadAnimation(NewsContentShowActivity.this, R.anim.web_load_fram5_anim);
		/*animation1.setStartTime(0);
		animation2.setStartTime(100);
		animation3.setStartTime(200);
		animation4.setStartTime(300);
		animation5.setStartTime(400);*/
		load_fram5.startAnimation(animation5);
		load_fram4.startAnimation(animation4);
		load_fram3.startAnimation(animation3);
		load_fram2.startAnimation(animation2);
		load_fram1.startAnimation(animation1);
	}
	private void initView() {
		// TODO Auto-generated method stub
		webView = (WebView)this.findViewById(R.id.news_content_show_webview);
		backButton = (ImageView)this.findViewById(R.id.news_content_show_back_button);
		backButton.setOnClickListener(this);
		load_fram1 = (ImageView)this.findViewById(R.id.load_fram1);
		load_fram2 = (ImageView)this.findViewById(R.id.load_fram2);
		load_fram3 = (ImageView)this.findViewById(R.id.load_fram3);
		load_fram4 = (ImageView)this.findViewById(R.id.load_fram4);
		load_fram5 = (ImageView)this.findViewById(R.id.load_fram5);
		loadRootView = (RelativeLayout)this.findViewById(R.id.load_rootView);
		webViewLoadProgres = (ProgressBar)this.findViewById(R.id.news_content_show_progressBar);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.news_content_show_back_button:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.e("c", "onTouchEvent:" + event.getAction());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startPositionX = (int) event.getX();
			startPositionY = (int) event.getY();
		case MotionEvent.ACTION_MOVE:
			distanceX = (int) (event.getX() - startPositionX);
			distanceY = (int) (event.getY() - startPositionY);
			break;

		case MotionEvent.ACTION_UP:
			Log.e("c", "MotionEvent.ACTION_UP:" + distanceX + " + " + distanceY);
			if(Math.abs(distanceY) < 100 && distanceX > 120){
				finish();
			}
			break;
		}
		return false;
	}
}

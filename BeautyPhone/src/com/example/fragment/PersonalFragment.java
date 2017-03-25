package com.example.fragment;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.example.activity.UserLoginUtil;
import com.example.adapter.PersonalListAdpater;
import com.example.adapter.PersonalListAdpater.onNewsClickListener;
import com.example.beautyphone.BroadCaseReceiverFilter;
import com.example.beautyphone.R;
import com.example.beautyphone.network.BaiSiVideoHtmlUtil;
import com.example.beautyphone.network.HtmlVideoBean;
import com.example.beautyphone.network.Bean.PersonalMyInfoBean;
import com.example.beautyphone.network.Bean.UserLoginResponse;
import com.example.beautyphone.news.bean.JuHeNewsDataManager;
import com.example.beautyphone.news.bean.JuHeNewsJsonData;
import com.example.beautyphone.news.bean.JuHeNewsJsonItemBean;
import com.example.beautyphone.news.bean.JuHeNewsRecycleItemBean;
import com.example.beautyphone.news.bean.NewsDataItemBean;
import com.example.beautyphone.news.bean.NewsDataManager;
import com.example.beautyphone.news.bean.NewsJsonItemBean;
import com.example.beautyphone.news.bean.NewsRecylceTitleBean;
import com.example.beautyphone.news.bean.PersonalRecycleBaseBean;
import com.example.beautyphone.news.bean.PersonalRecycleItemBean;
import com.example.myDialog.LoadingDialog;
import com.example.util.ShareDpreferenceUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalFragment extends Fragment implements OnClickListener{
	private NewsDataItemBean newSbean = null;
	private JuHeNewsJsonData juHeNewsBean = null;
	private String newsType = "ent";
	private NewsDataManager dataManager = null;
	private JuHeNewsDataManager juHeDataManager = null;
	private RecyclerView recycleView = null;
	private int firstVisibleItem = -1;
	private int lastVisibleItem = -1;
	private LinearLayoutManager layoutOutMamager = null;
	private LinearLayout newTitleLayout = null;
	private PersonalMyInfoBean meHead = null;
	private NewsRecylceTitleBean newsTitle = null;
	private PersonalListAdpater recylceAdapter = null;
	private ArrayList<PersonalRecycleBaseBean> list = null;
	private MyInfoChangeBroadCase loginSuccessBroadCase = null;
	private MyInfoChangeBroadCase paySuccessBroadCase = null;
	private RelativeLayout rootView = null;
	private LoadingDialog loadDialog = null;
	private int listType = 0;	//新闻列表类型，默认是0：文字新闻，1：视频
	private TextView topTextTitle = null;
	private TextView videoTextTitle = null;
	private TextView shehuiTextTitle = null;
	private TextView guoneiTextTitle = null;
	private TextView guojiTextTitle = null;
	private TextView yuleTextTitle = null;
	private TextView tiyuTextTitle = null;
	private TextView junshiTextTitle = null;
	private TextView kejiTextTitle = null;
	private TextView caijingTextTitle = null;
	private TextView shishangTextTitle = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.personal_fragment, container, false);
		initView(view);
		initViewData();
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	private void initViewData() {
		// TODO Auto-generated method stub
		loadDialog = new LoadingDialog(getActivity());
		loginSuccessBroadCase = new MyInfoChangeBroadCase();
		paySuccessBroadCase = new MyInfoChangeBroadCase();
		getActivity().registerReceiver(loginSuccessBroadCase, new IntentFilter(BroadCaseReceiverFilter.LOGIN_SUCCESS_FILTER));
		getActivity().registerReceiver(paySuccessBroadCase, new IntentFilter(BroadCaseReceiverFilter.PAY_SUCCESS_FILTER));
		//dataManager = new NewsDataManager();
		juHeDataManager = new JuHeNewsDataManager();
		recycleView.setOnScrollListener(onScrollListener);
		list = new ArrayList<PersonalRecycleBaseBean>();
		meHead = new PersonalMyInfoBean();
		meHead.setItemType(0);
		list.add(meHead);
		newsTitle = new NewsRecylceTitleBean();
		newsTitle.setItemType(1);
		list.add(newsTitle);
		recylceAdapter = new PersonalListAdpater(getActivity(), list);
		layoutOutMamager = new LinearLayoutManager(getActivity());
		recycleView.setLayoutManager(layoutOutMamager);
		recycleView.setHasFixedSize(true);
		DefaultItemAnimator anima = new DefaultItemAnimator();
		recycleView.setItemAnimator(anima);
		recycleView.setAdapter(recylceAdapter);
		initNewsData("top");
		recylceAdapter.setonNewsTitleClickListener(newTitlelister);
		UserLoginUtil.getInstance(getActivity()).loginAute();
	}

	private onNewsClickListener newTitlelister = new onNewsClickListener(){

		@Override
		public void newsClickListener(int index, String type) {
			// TODO Auto-generated method stub
			Log.e("c", index + " + " + type);
			initNewsData(type);
		}

	};

	private OnScrollListener onScrollListener = new OnScrollListener() {
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
			if (layoutManager instanceof LinearLayoutManager) {
				LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
				//int lastItemPosition = linearManager.findLastVisibleItemPosition();
				int firstItemPosition = linearManager.findFirstVisibleItemPosition();
				int lastItemPotition = linearManager.findLastVisibleItemPosition();
				View view = recyclerView.getChildAt(1);
				if(firstItemPosition == 0){
					if(newTitleLayout.getVisibility() == View.VISIBLE){
						newTitleLayout.setVisibility(View.GONE);
					}
				}else{
					if(newTitleLayout.getVisibility() == View.GONE){
						newTitleLayout.setVisibility(View.VISIBLE);
					}
				}

				if(lastItemPotition >= linearManager.getItemCount() - 1){
					if(lastVisibleItem != lastItemPotition && listType == 1){
						lastVisibleItem = lastItemPotition;
						loadMoreVideo();
					}
				}
			}
		};

	};

	private void initView(View view) {
		// TODO Auto-generated method stub
		recycleView = (RecyclerView) view.findViewById(R.id.personalfragment_main_recylceview);
		rootView = (RelativeLayout)view.findViewById(R.id.personalfragment_contact_rootview);
		newTitleLayout = (LinearLayout)view.findViewById(R.id.personalfragment_news_title_layout);
		topTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_top);
		shehuiTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_shehui);
		guoneiTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_guonei);
		guojiTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_guoji);
		yuleTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_yule);
		tiyuTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_tiyu);
		junshiTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_junshi);
		kejiTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_keji);
		caijingTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_caijing);
		shishangTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_shishang);
		videoTextTitle = (TextView)view.findViewById(R.id.personalfragment_news_title_video);

		topTextTitle.setOnClickListener(this);
		videoTextTitle.setOnClickListener(this);
		shehuiTextTitle.setOnClickListener(this);
		guoneiTextTitle.setOnClickListener(this);
		guojiTextTitle.setOnClickListener(this);
		yuleTextTitle.setOnClickListener(this);
		tiyuTextTitle.setOnClickListener(this);
		junshiTextTitle.setOnClickListener(this);
		kejiTextTitle.setOnClickListener(this);
		caijingTextTitle.setOnClickListener(this);
		shishangTextTitle.setOnClickListener(this);

	}

	private void initNewsData(String newType){
		/*		if(dataManager== null){
			dataManager = new NewsDataManager();
		}*/
		if(newType.equals("video")){
			listType = 1;
		}else{
			listType = 0;
		}
		if(juHeDataManager== null){
			juHeDataManager = new JuHeNewsDataManager();
		}
		switch (newType) {
		case "video":
			SetNewsTitleColor(newType);
			loadVideo();
			loadDialog.showDialog();
			break;
		case "top":
			SetNewsTitleColor(newType);
			juHeDataManager.getTop(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("top")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getTop();
						juHeNewsBean = dataItemBean;
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "shehui":
			SetNewsTitleColor(newType);
			juHeDataManager.getShehui(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("shehui")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getShehui();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "guonei":
			SetNewsTitleColor(newType);
			juHeDataManager.getGuonei(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("guonei")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getGuonei();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "guoji":
			SetNewsTitleColor(newType);
			juHeDataManager.getGuoji(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("guoji")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getGuoji();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "yule":
			SetNewsTitleColor(newType);
			juHeDataManager.getYule(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("yule")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getYule();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "tiyu":
			SetNewsTitleColor(newType);
			juHeDataManager.getTiyu(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("tiyu")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getTiyu();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "junshi":
			SetNewsTitleColor(newType);
			juHeDataManager.getJunshi(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("junshi")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getJunshi();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "keji":
			SetNewsTitleColor(newType);
			juHeDataManager.getKeji(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("keji")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getKeji();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "caijing":
			SetNewsTitleColor(newType);
			juHeDataManager.getCaijing(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("caijing")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getCaijing();
						LoadList(dataItemBean);
					}
				}
			});
			break;
		case "shishang":
			SetNewsTitleColor(newType);
			juHeDataManager.getShishang(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					Bundle bundle = msg.getData();
					if(bundle != null && bundle.getBoolean("shishang")){
						JuHeNewsJsonData dataItemBean = juHeDataManager.getShishang();
						LoadList(dataItemBean);
					}
				}
			});
			break;

		}
	}


	private void loadVideo() {
		// TODO Auto-generated method stub
		Log.e("c", "加载视频");
		BaiSiVideoHtmlUtil.getInstance().getVideoBenaList(loadVideoHandler);
		//Htm
	}
	private Handler loadVideoHandler = new Handler(){
		public void handleMessage(Message msg) {
			loadDialog.closeDialog();
			if(msg != null && msg.what == 2){
				Toast.makeText(getActivity(), "后面没有啦", 0).show();
				return;
			}
			if(msg.obj != null && msg.what == 0){
				ArrayList<HtmlVideoBean> videoList = (ArrayList<HtmlVideoBean>) msg.obj;
				if(videoList != null && videoList.size() > 0){
					LoadVideoList(videoList);
				}
			}
		};
	};

	private void loadMoreVideo(){
		Log.e("c", "加载跟多视频视频");
		BaiSiVideoHtmlUtil.getInstance().getMoreVideoBenaList(loadVideoHandler);
	}

	private void SetNewsTitleColor(String type) {
		newsTitle.setShowNewsType(type);
		switch (type) {
		case "video":
			videoTextTitle.setTextColor(Color.rgb(255, 138, 0));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "top":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(255, 138, 0));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "shehui":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(255, 138, 0));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "guonei":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(255, 138, 0));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "guoji":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(255, 138, 0));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "yule":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(255, 138, 0));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "tiyu":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(255, 138, 0));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "junshi":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(255, 138, 0));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "keji":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(255, 138, 0));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "caijing":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(255, 138, 0));
			shishangTextTitle.setTextColor(Color.rgb(102, 102, 102));
			break;
		case "shishang":
			videoTextTitle.setTextColor(Color.rgb(102, 102, 102));
			topTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shehuiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guoneiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			guojiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			yuleTextTitle.setTextColor(Color.rgb(102, 102, 102));
			tiyuTextTitle.setTextColor(Color.rgb(102, 102, 102));
			junshiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			kejiTextTitle.setTextColor(Color.rgb(102, 102, 102));
			caijingTextTitle.setTextColor(Color.rgb(102, 102, 102));
			shishangTextTitle.setTextColor(Color.rgb(255, 138, 0));
			break;
		}

	}

	private void LoadVideoList(ArrayList<HtmlVideoBean> dataItemBean) {
		// TODO Auto-generated method stub
		if(list == null){
			list = new ArrayList<PersonalRecycleBaseBean>();
		}else{
			list.clear();
		}
		list.add(meHead);
		list.add(newsTitle);
		list.addAll(dataItemBean);
		recylceAdapter.notifyDataSetChanged();
	}

	private void LoadList(JuHeNewsJsonData dataItemBean) {
		// TODO Auto-generated method stub
		juHeNewsBean = dataItemBean;
		if(list == null){
			list = new ArrayList<PersonalRecycleBaseBean>();
		}else{
			list.clear();
		}
		list.add(meHead);
		list.add(newsTitle);
		for (int i = 0; i < dataItemBean.getResult().getData().size(); ++i){
			JuHeNewsJsonItemBean n = dataItemBean.getResult().getData().get(i);
			JuHeNewsRecycleItemBean itemBean = new JuHeNewsRecycleItemBean();
			int listSize = list.size();
			if(!n.getThumbnail_pic_s().isEmpty() && 
					!n.getThumbnail_pic_s02().isEmpty() &&
					!n.getThumbnail_pic_s03().isEmpty()){
				if((listSize - 1 >= 0 && list.get(listSize - 1).getItemType() != 3) ||
						((listSize - 2) >= 0 && list.get(listSize - 2).getItemType() != 3)){
					itemBean.setItemType(3);
				}else{
					itemBean.setItemType(2);
				}
			}else{
				itemBean.setItemType(2);
			}
			itemBean.setItemBean(n);
			itemBean.setNewsType(dataItemBean.getNewType());
			list.add(itemBean);
		}
		recylceAdapter.notifyDataSetChanged();
	}
	/*	private void LoadList(NewsDataItemBean dataItemBean) {
		// TODO Auto-generated method stub
		newSbean = dataItemBean;
		if(list == null){
			list = new ArrayList<PersonalRecycleBaseBean>();
		}else{
			list.clear();
		}
		list.add(meHead);
		list.add(newsTitle);
		for (NewsJsonItemBean n:dataItemBean.getList()){
			PersonalRecycleItemBean itemBean = new PersonalRecycleItemBean();
			itemBean.setItemBean(n);
			itemBean.setItemType(2);
			itemBean.setNewsType(dataItemBean.getNewsType());
			list.add(itemBean);
		}
		recylceAdapter.notifyDataSetChanged();
	}
	 */

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.personalfragment_news_title_video:
			initNewsData("video");
			break;
		case R.id.personalfragment_news_title_top:
			initNewsData("top");
			break;
		case R.id.personalfragment_news_title_shehui:
			initNewsData("shehui");
			break;
		case R.id.personalfragment_news_title_guonei:
			initNewsData("guonei");
			break;
		case R.id.personalfragment_news_title_guoji:
			initNewsData("guoji");
			break;
		case R.id.personalfragment_news_title_yule:
			initNewsData("yule");
			break;
		case R.id.personalfragment_news_title_tiyu:
			initNewsData("tiyu");
			break;
		case R.id.personalfragment_news_title_junshi:
			initNewsData("junshi");
			break;
		case R.id.personalfragment_news_title_keji:
			initNewsData("keji");
			break;
		case R.id.personalfragment_news_title_caijing:
			initNewsData("caijing");
			break;
		case R.id.personalfragment_news_title_shishang:
			initNewsData("shishang");
			break;

		}
	}

	public class MyInfoChangeBroadCase extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			ShareDpreferenceUtil dpreferenceUtil = new ShareDpreferenceUtil();
			String userJson = dpreferenceUtil.getShareData(getActivity(), "userInfo");
			Log.e("c", "数据更新广播：" + userJson);
			if(!userJson.isEmpty()){
				JSONObject jsonObject = JSONObject.parseObject(userJson);
				UserLoginResponse loginResponse = JSONObject.toJavaObject(jsonObject, UserLoginResponse.class);
				meHead.setBlack_calltime(loginResponse.getMaxPhoneTime());
				meHead.setVipCount(loginResponse.getUserVipState());
				meHead.setItemType(0);
				meHead.setConverImageUrl("");
				meHead.setBlack_money(loginResponse.getBalanceMoney());
				meHead.setUserAccount(loginResponse.getUserAccount());
				recylceAdapter.notifyDataSetChanged();
			}
		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(loginSuccessBroadCase);
		getActivity().unregisterReceiver(paySuccessBroadCase);
	}
}

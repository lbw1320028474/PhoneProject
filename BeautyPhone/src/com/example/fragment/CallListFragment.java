package com.example.fragment;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.activity.IsCallIngActivity;
import com.example.adapter.CallListAdapter;
import com.example.adapter.CallListAdapter.onClickLister;
import com.example.adapter.CallListAdapter.onLongClickLister;
import com.example.adapter.CallListAdapter.selectedListener;
import com.example.beautyphone.BroadCaseReceiverFilter;
import com.example.beautyphone.R;
import com.example.beautyphone.StaticObject;
import com.example.contacts.CallLogBean;
import com.example.contacts.CallSearchResultBean;
import com.example.contacts.ContactPeopleBean;
import com.example.db.ContactsUtil;
import com.example.myDialog.CallTypeChoseDialog;
import com.example.myview.CustomLinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;


public class CallListFragment extends Fragment implements OnClickListener, OnLongClickListener{
	private numberboardListener boardListener = null;
	private int downToolViewHeight = 0;
	private LinearLayout downToolLayout = null;
	private RecyclerView recycleView= null;
	private CallListAdapter callLogAdapter = null;
	private CustomLinearLayoutManager layoutmanager = null;
	private LinearLayout allCallButtonView = null;	//全部通话
	private LinearLayout missCallButtonView = null;
	private LinearLayout strangeCallButtonView = null;
	private ArrayList<CallLogBean> allListData = null;
	private ArrayList<CallLogBean> missListData = null;
	private ArrayList<CallLogBean> strangeListData = null;
	private ArrayList<CallLogBean> searchResultListData = null;
	private ArrayList<CallLogBean> nowIsShowList = null;
	private LinearLayout callLogFragmentCallBoard = null;
	private LinearLayout callLogFragmentLogEdit = null;
	private LinearLayout callLogFragmentMenu = null;
	private CallTypeChoseDialog callTypeChoseDialog = null;
	private CallLogUpdata callLogUpdata = null;
	//拨号盘控件
	private LinearLayout numberBoardRootView = null;
	private boolean numberBoardAnimIsFinish = true;
	private LinearLayout numberButton1 = null;
	private LinearLayout numberButton2 = null;
	private LinearLayout numberButton3 = null;
	private LinearLayout numberButton4 = null;
	private LinearLayout numberButton5 = null;
	private LinearLayout numberButton6 = null;
	private LinearLayout numberButton7 = null;
	private LinearLayout numberButton8 = null;
	private LinearLayout numberButton9 = null;
	private LinearLayout numberButton0 = null;
	private LinearLayout numberButtonx = null;
	private LinearLayout numberButtonj = null;
	private LinearLayout board_close = null;
	private LinearLayout board_call = null;
	private LinearLayout board_delete = null;
	private LinearLayout selected_all = null;
	private LinearLayout delete_selected = null;
	private boolean isSected = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.calllog_fragment, container, false);
		initView(view);
		initViewData();
		return view;
	}

	public class CallLogUpdata extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(IsCallIngActivity.bean != null){
				CallLogBean callLogBean = ContactsUtil.getCallLogByUri(IsCallIngActivity.newCallLogUri);
				if(callLogBean == null){
					return;
				}
				if(callLogBean.getLogType() == 3){
					missListData.add(0, callLogBean);
				}else if(callLogBean.getName().isEmpty()){
					strangeListData.add(0, callLogBean);
				}
				allListData.add(0, callLogBean);
				callLogAdapter.notifyDataSetChanged();
			}
		}

	}

	private void initViewData() {
		// TODO Auto-generated method stub
		callLogUpdata = new CallLogUpdata();
		allListData = new ArrayList<CallLogBean>();
		allListData = ContactsUtil.getCallLogList(getActivity());
		getActivity().registerReceiver(callLogUpdata, new IntentFilter(BroadCaseReceiverFilter.CALLLOG_UPDATAED));
		UpDataList(allListData);
		groupByCallType();
	}
	public void UpDataList(ArrayList<CallLogBean> listData) {
		CallLogBean lastItem = new CallLogBean();	//最后一个调整位置的item
		lastItem.setItemType(1);
		listData.add(lastItem);
		nowIsShowList = listData;
		callLogAdapter = new CallListAdapter(getActivity(), nowIsShowList, downToolViewHeight);
		callLogAdapter.setonClickLister(new onClickLister() {

			@Override
			public void click(int position, int itemType) {
				// TODO Auto-generated method stub
				Log.e("c", "点击位置" + position + " + " + itemType);
				switch (itemType) {
				case 0:
					callTypeChoseDialog.showDialog(callLogAdapter.listData.get(position).getNumber(), 
							callLogAdapter.listData.get(position).getName(),callLogAdapter.listData.get(position).getNumberAdress());
					break;
				case 1:
					boardListener.clickType("itemclick", 0, (CallSearchResultBean) searchResultListData.get(position));
					break;
				}
			}
		});
		callLogAdapter.setonLongClickLister(new onLongClickLister() {

			@Override
			public void longClick(int position, int itemType) {
				// TODO Auto-generated method stub
				Log.e("c", "长按位置" + position + " + " + itemType);
				switch (itemType) {
				case 0:

					break;

				case 1:
					break;
				}
			}
		});
		layoutmanager = new CustomLinearLayoutManager(getActivity());
		recycleView.setLayoutManager(layoutmanager);
		recycleView.setHasFixedSize(true);
		callLogAdapter.setonItemSeletedListener(itemSelectedLitener);
		DefaultItemAnimator anima = new DefaultItemAnimator();
		recycleView.setItemAnimator(anima);
		recycleView.setAdapter(callLogAdapter);
	}

	private CallListAdapter.selectedListener itemSelectedLitener = new selectedListener() {

		@Override
		public void seleted(int selectedPosition, boolean sNs) {
			// TODO Auto-generated method stub
			Log.e("C", "选着的position：" + selectedPosition);
			if(nowIsShowList.get(selectedPosition).isSelected()){
				nowIsShowList.get(selectedPosition).setSelected(false);
			}else{
				nowIsShowList.get(selectedPosition).setSelected(true);
			}
			callLogAdapter.notifyDataSetChanged();
		}
	};

	public void beginToEdit(boolean b){
		for (int i = 0; i < nowIsShowList.size(); ++i){
			nowIsShowList.get(i).setEdit(b);
			if(!b){
				nowIsShowList.get(i).setSelected(false);
			}
		}
		callLogAdapter.notifyDataSetChanged();
	}

	/**
	 * 对数据进行分组
	 */
	private void groupByCallType() {
		// TODO Auto-generated method stub
		missListData = new ArrayList<CallLogBean>();
		strangeListData = new ArrayList<CallLogBean>();
		if(allListData != null && allListData.size() > 0){
			for (CallLogBean c: allListData){
				if(c.getLogType() == 3){
					missListData.add(c);
				}
				if(c.getName().equals("")){
					strangeListData.add(c);
				}
			}
		}
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		callTypeChoseDialog = new CallTypeChoseDialog(getActivity());
		selected_all = (LinearLayout)view.findViewById(R.id.calllogfragment_selectedall);
		delete_selected = (LinearLayout)view.findViewById(R.id.calllogfragment_delete);
		recycleView = (RecyclerView)view.findViewById(R.id.calllogfragment_recylceview);
		downToolLayout = (LinearLayout)view.findViewById(R.id.calllogfragment__donw_tool_layout);
		allCallButtonView = (LinearLayout)view.findViewById(R.id.calllogfragment_allcalllog);
		missCallButtonView = (LinearLayout)view.findViewById(R.id.calllogfragment_misscalllog);
		strangeCallButtonView = (LinearLayout)view.findViewById(R.id.calllogfragment_astrangenumber);
		callLogFragmentCallBoard = (LinearLayout)view.findViewById(R.id.calllogfragment_numberboard);
		callLogFragmentLogEdit = (LinearLayout)view.findViewById(R.id.calllogfragment_edit);
		callLogFragmentMenu = (LinearLayout)view.findViewById(R.id.calllogfragment_menu);
		selected_all.setOnClickListener(this);
		delete_selected.setOnClickListener(this);
		callLogFragmentCallBoard.setOnClickListener(this);
		callLogFragmentLogEdit.setOnClickListener(this);
		callLogFragmentMenu.setOnClickListener(this);
		initNumberBoardView(view);
		recycleView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				if(((String)(recyclerView.getTag())).equals("logRecycleView")){
					//boardListener.clickType("close", 0);
					//numberBoardRootView.setVisibility(View.GONE);
					if(numberBoardAnimIsFinish){
						startNumberBoardCloseAnim(numberBoardRootView);
					}
				}
			}
		});
		recycleView.setTag("logRecycleView");
		allCallButtonView.setOnClickListener(this);
		missCallButtonView.setOnClickListener(this);
		strangeCallButtonView.setOnClickListener(this);
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		downToolLayout.measure(w, h);
		android.widget.RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) downToolLayout.getLayoutParams();
		this.downToolViewHeight = downToolLayout.getMeasuredHeight() + layoutParams.bottomMargin;
		Log.e("c", "height:" + ((android.widget.RelativeLayout.LayoutParams) downToolLayout.getLayoutParams()));
	}

	private void initNumberBoardView(View view) {
		// TODO Auto-generated method stub
		numberBoardRootView = (LinearLayout)view.findViewById(R.id.number_board_root_layout);
		numberButton0 = (LinearLayout)view.findViewById(R.id.numberboard_number0);
		numberButton1 = (LinearLayout)view.findViewById(R.id.numberboard_number1);
		numberButton2 = (LinearLayout)view.findViewById(R.id.numberboard_number2);
		numberButton3 = (LinearLayout)view.findViewById(R.id.numberboard_number3);
		numberButton4 = (LinearLayout)view.findViewById(R.id.numberboard_number4);
		numberButton5 = (LinearLayout)view.findViewById(R.id.numberboard_number5);
		numberButton6 = (LinearLayout)view.findViewById(R.id.numberboard_number6);
		numberButton7 = (LinearLayout)view.findViewById(R.id.numberboard_number7);
		numberButton8 = (LinearLayout)view.findViewById(R.id.numberboard_number8);
		numberButton9 = (LinearLayout)view.findViewById(R.id.numberboard_number9);
		numberButtonx = (LinearLayout)view.findViewById(R.id.numberboard_numberx);
		numberButtonj = (LinearLayout)view.findViewById(R.id.numberboard_numberj);
		board_close = (LinearLayout)view.findViewById(R.id.numberboard_close);
		board_call = (LinearLayout)view.findViewById(R.id.numberboard_call_button);
		board_delete = (LinearLayout)view.findViewById(R.id.numberboard_delete);
		numberButton0.setOnClickListener(this);
		numberButton1.setOnClickListener(this);
		numberButton2.setOnClickListener(this);
		numberButton3.setOnClickListener(this);
		numberButton4.setOnClickListener(this);
		numberButton5.setOnClickListener(this);
		numberButton6.setOnClickListener(this);
		numberButton7.setOnClickListener(this);
		numberButton8.setOnClickListener(this);
		numberButton9.setOnClickListener(this);
		numberButtonx.setOnClickListener(this);
		numberButtonj.setOnClickListener(this);
		board_close.setOnClickListener(this);
		board_call.setOnClickListener(this);
		board_delete.setOnClickListener(this);
		board_delete.setOnLongClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.calllogfragment_selectedall:
			if(isSected){
				toSelectedAll(false);
				isSected = false;
			}else{
				toSelectedAll(true);
				isSected = true;
			}
			break;
		case R.id.calllogfragment_delete:
			beginToDelete();
			break;
		case R.id.calllogfragment_allcalllog:
			showAllCallLog();
			break;
		case R.id.calllogfragment_misscalllog:
			showMissCallLog();
			break;
		case R.id.calllogfragment_astrangenumber:
			showStrangeCallLog();
			break;
			//以下是拨号盘的控件
		case R.id.calllogfragment_numberboard:
			//numberBoardRootView.setVisibility(View.VISIBLE);
			startNumberBoardOpenAnim(numberBoardRootView);
			if(boardListener != null){
				boardListener.clickType("showboard", 0, null);
			}
			break;
		case R.id.calllogfragment_edit:
			//numberBoardRootView.setVisibility(View.VISIBLE);
			/*startNumberBoardOpenAnim(numberBoardRootView);
			if(boardListener != null){
				boardListener.clickType("showboard", 0, null);
			}*/
			if(selected_all.getVisibility() == View.VISIBLE){
				selected_all.setVisibility(View.GONE);
				delete_selected.setVisibility(View.GONE);
				beginToEdit(false);
				isSected = false;
			}else{
				selected_all.setVisibility(View.VISIBLE);
				delete_selected.setVisibility(View.VISIBLE);
				beginToEdit(true);
				isSected = false;
			}
			break;
		case R.id.calllogfragment_menu:
			//numberBoardRootView.setVisibility(View.VISIBLE);
			startNumberBoardOpenAnim(numberBoardRootView);
			if(boardListener != null){
				boardListener.clickType("showboard", 0, null);
			}
			break;
		case R.id.numberboard_number0:
			if(boardListener != null){
				boardListener.clickType("0", 0, null);
			}
			break;
		case R.id.numberboard_number1:
			if(boardListener != null){
				boardListener.clickType("1", 0, null);
			}
			break;
		case R.id.numberboard_number2:
			if(boardListener != null){
				boardListener.clickType("2", 0, null);
			}
			break;
		case R.id.numberboard_number3:
			if(boardListener != null){
				boardListener.clickType("3", 0, null);
			}
			break;
		case R.id.numberboard_number4:
			if(boardListener != null){
				boardListener.clickType("4", 0, null);
			}
			break;
		case R.id.numberboard_number5:
			if(boardListener != null){
				boardListener.clickType("5", 0, null);
			}
			break;
		case R.id.numberboard_number6:
			if(boardListener != null){
				boardListener.clickType("6", 0, null);
			}
			break;
		case R.id.numberboard_number7:
			if(boardListener != null){
				boardListener.clickType("7", 0, null);
			}
			break;
		case R.id.numberboard_number8:
			if(boardListener != null){
				boardListener.clickType("8", 0, null);
			}
			break;
		case R.id.numberboard_number9:
			if(boardListener != null){
				boardListener.clickType("9", 0, null);
			}
			break;
		case R.id.numberboard_numberx:
			if(boardListener != null){
				boardListener.clickType("*", 0, null);
			}
			break;
		case R.id.numberboard_numberj:
			if(boardListener != null){
				boardListener.clickType("#", 0, null);
			}
			break;
		case R.id.numberboard_close:
			//numberBoardRootView.setVisibility(View.GONE);
			startNumberBoardCloseAnim(numberBoardRootView);
			if(boardListener != null){
				boardListener.clickType("close", 0, null);
			}
			break;
		case R.id.numberboard_call_button:
			if(boardListener != null){
				boardListener.clickType("call", 0, null);
			}
			break;
		case R.id.numberboard_delete:
			if(boardListener != null){
				boardListener.clickType("delete", 0, null);
			}
			break;
		}
	}
	private void beginToDelete() {
		// TODO Auto-generated method stub
		ArrayList<String> delete = new ArrayList<String>();
		for (int i = nowIsShowList.size() - 1; i >= 0; --i){
			CallLogBean c = nowIsShowList.get(i);
			if(c.isEdit() && c.isSelected()){
				delete.add(c.getCallogId() + "");
				nowIsShowList.remove(i);
				callLogAdapter.notifyItemRemoved(i);
			}
		}
		int row = ContactsUtil.deleteCallLog(getActivity(), delete);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				uiHandler.sendMessage(message);
			}
		}, 550);
		Log.e("c", "删除了:" + row);
	}

	private Handler uiHandler = new Handler(){
		public void handleMessage(Message msg) {
			callLogAdapter.notifyDataSetChanged();
		};
	};

	//多选编辑时选着所有的方法
	private void toSelectedAll(boolean isSelected) {
		// TODO Auto-generated method stub
		if(nowIsShowList == null){
			Log.e("C", "数据列表为空");
			return;
		}
		for (int i = 0; i < nowIsShowList.size(); ++i){
			nowIsShowList.get(i).setSelected(isSelected);
		}
		callLogAdapter.notifyDataSetChanged();
	}
	public void showDefaultListData(){
		UpDataList(allListData);
	}

	public void updataSearchDataListData(String inputedNumber){
		//Log.e("c", "已输入的号码inputNumber: " + inputedNumber);
		if(StaticObject.peopleAllListData != null && inputedNumber.length() > 0){
			getSearchResult(inputedNumber, StaticObject.peopleAllListData, handler);
		}
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(searchResultListData == null){
				return;
			}
			UpDataList(searchResultListData);
		};
	};

	//对搜索结果进行匹配
	private void getSearchResult(final String inputedNumber, ArrayList<ContactPeopleBean> peopleAllListData, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message = new Message();
				Bundle bundle = new Bundle();
				searchResultListData = new ArrayList<CallLogBean>();
				for (ContactPeopleBean bean: StaticObject.peopleAllListData){
					if(bean == null){
						Log.e("", "bean为空");
					}
					Log.e("c", bean.getNamePinyin());
					ArrayList<String> numberList = bean.getNumberList();
					CallSearchResultBean b = new CallSearchResultBean();
					if(numberList != null && numberList.size() > 0){
						for (String nums: numberList){
							if(nums.indexOf(inputedNumber) >= 0){
								b.setContact_Id(bean.getContactId());
								b.setContact_name(bean.getName());
								b.setNumberList(bean.getNumberList());
								b.setContactCover_Id(bean.getContactCoverId());
								b.setContactCover_Bitmap(bean.getContactCoverBitmap());
								b.setNumberList(bean.getNumberList());
								b.setItemType(2);
								b.setSerchNumber(inputedNumber);
								searchResultListData.add(b);
								break;
							}
						}
						if(bean.getNamePinyin().indexOf(inputedNumber) >= 0){
							addContactToSerchList(inputedNumber, bean, b);
						}
					}else if(bean.getNamePinyin().indexOf(inputedNumber) >= 0){
						addContactToSerchList(inputedNumber, bean, b);
					}
				}
				bundle.putString("ok", "ok");
				message.setData(bundle);
				handler.sendMessage(message);
			}
		}).start();
	}

	private void addContactToSerchList(String inputedNumber, ContactPeopleBean bean, CallSearchResultBean b) {
		b.setContact_Id(bean.getContactId());
		b.setContact_name(bean.getName());
		b.setSerchNumber(inputedNumber);
		b.setNumberList(bean.getNumberList());
		b.setContactCover_Id(bean.getContactCoverId());
		b.setContactCover_Bitmap(bean.getContactCoverBitmap());
		b.setItemType(2);
		searchResultListData.add(b);
	}
	private void showStrangeCallLog() {
		// TODO Auto-generated method stub
		UpDataList(strangeListData);
	}
	private void showMissCallLog() {
		// TODO Auto-generated method stub
		UpDataList(missListData);

	}
	private void showAllCallLog() {
		// TODO Auto-generated method stub
		UpDataList(allListData);
	}

	public void setonNumberBoardListener(numberboardListener bListener){
		this.boardListener = bListener;
	}

	public interface numberboardListener{
		/**
		 * 
		 * @param numberChar 按下的按钮
		 * @param doType 按下类型,0--代表单击，1--长按
		 */
		public void clickType(String numberChar, int doType, CallSearchResultBean bean);
	}

	public void showNumberBoard(){
		if(numberBoardRootView != null && numberBoardRootView.getVisibility() == View.GONE){
			//numberBoardRootView.setVisibility(View.VISIBLE);
			startNumberBoardOpenAnim(numberBoardRootView);
		}
	}
	public void closeNumberBoard(){
		if(numberBoardRootView != null && numberBoardRootView.getVisibility() == View.VISIBLE){
			//numberBoardRootView.setVisibility(View.GONE);
			startNumberBoardCloseAnim(numberBoardRootView);
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.numberboard_delete:
			boardListener.clickType("delete", 1, null);
			break;

		default:
			break;
		}
		return true;
	}


	/**
	 * 拨号盘控件出现动画
	 */
	public void startNumberBoardOpenAnim(final View v){
		if(v.getVisibility() == View.VISIBLE){
			return;
		}
		v.setVisibility(View.VISIBLE);
		Log.e("c", "打开动画");
		v.clearAnimation();
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(400);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 150, 0);
		translateAnimation.setDuration(400);
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
	 * 拨号盘控件消失动画
	 */
	public void startNumberBoardCloseAnim(final View v){
		if(v.getVisibility() == View.GONE){
			return;
		}
		v.clearAnimation();
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(300);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 150);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(translateAnimation);
		v.startAnimation(animationSet);
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				numberBoardAnimIsFinish = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				numberBoardAnimIsFinish = true;
				v.setVisibility(View.GONE);
			}
		});
	}

	/**
	 * 底部控件出现动画
	 */
	public void startBottomOpenAnim(final View v){
		v.setVisibility(View.VISIBLE);
		Log.e("c", "打开动画");
		v.clearAnimation();
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(300);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 30, 0);
		translateAnimation.setDuration(300);
		animationSet.addAnimation(alphaAnimation);
		animationSet.addAnimation(translateAnimation);
		v.startAnimation(animationSet);
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				numberBoardAnimIsFinish = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				numberBoardAnimIsFinish = true;
			}
		});
	}
	/**
	 * 底部工具条控件消失动画
	 */
	public void startBottomCloseAnim(final View v){
		v.clearAnimation();
		AnimationSet animationSet = new AnimationSet(true);
		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(400);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 30);
		translateAnimation.setDuration(400);
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
}

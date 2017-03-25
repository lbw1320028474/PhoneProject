package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.ContactsInfomationCalllogRecycleAdapter;
import com.example.beautyphone.R;
import com.example.contacts.ContactInfomationCalllogItemBean;
import com.example.contacts.ContactInfomationCllllogBaseBean;
import com.example.db.ContactsUtil;
import com.example.util.DataConverterUtil;
import com.example.util.DataConverterUtil.DataBean;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactInfomationCallLogFragment extends Fragment{
	private LinearLayout isEmpty = null;
	private RecyclerView logRecycleView = null;
	private LinearLayout dataAdjust = null;
	private LinearLayoutManager layoutManager = null;
	private ArrayList<ContactInfomationCllllogBaseBean> listData = null;
	private ContactsInfomationCalllogRecycleAdapter recycleviewAdpater = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.contactinfomation_calllog_layout, container, false);
		initView(view);
		initViewData();
		return view;
	}

	private void initViewData() {
		// TODO Auto-generated method stub
		listData = new ArrayList<ContactInfomationCllllogBaseBean>();
		recycleviewAdpater = new ContactsInfomationCalllogRecycleAdapter(getActivity(), listData);
		updataRecycleView();
	}

	public class CalllogChangeBroadCase extends ContentObserver{
		private static final String TAG = "ContactChangeBroadCase";
		private Context context = null;
		private Handler handler = null;
		public CalllogChangeBroadCase(Context context,Handler handler) {
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
			//beginToGetInfo();
		}

	}

	public void initRecycleView(String number){
		ContactsUtil.getCallLogByNumber(getActivity(), number, handler);
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1){
				if(ContactsUtil.subcalllogList != null && ContactsUtil.subcalllogList.size() > 0){
					Log.e("c", "通话记录数量" + ContactsUtil.subcalllogList.size());
					initGroupData();
				}
			}else{
				Log.e("c", "读取记录失败或者为空");
			}
		}

	};

	private void initGroupData() {
		// TODO Auto-generated method stub
		if(listData == null){
			listData = new ArrayList<ContactInfomationCllllogBaseBean>();
		}else{
			listData.clear();
		}
		if(ContactsUtil.subcalllogList != null && ContactsUtil.subcalllogList.size() > 0){
			DataBean dataBean= new DataBean();
			for (int i = 0; i < ContactsUtil.subcalllogList.size(); ++i){
				//for (ContactInfomationCalllogItemBean c:ContactsUtil.subcalllogList){
				//Log.e("c", c.getDataBean().toString());
				ContactInfomationCalllogItemBean newBean = new ContactInfomationCalllogItemBean();
				newBean.setCallLogdate(ContactsUtil.subcalllogList.get(i).getCallLogdate());
				newBean.setCallogId(ContactsUtil.subcalllogList.get(i).getCallogId());
				newBean.setDataBean(ContactsUtil.subcalllogList.get(i).getDataBean());
				newBean.setDuration(ContactsUtil.subcalllogList.get(i).getDuration());
				newBean.setItemType(ContactsUtil.subcalllogList.get(i).getItemType());
				newBean.setLogType(ContactsUtil.subcalllogList.get(i).getLogType());
				newBean.setName(ContactsUtil.subcalllogList.get(i).getName());
				newBean.setNumber(ContactsUtil.subcalllogList.get(i).getNumber());
				newBean.setNumberAdress(ContactsUtil.subcalllogList.get(i).getNumberAdress());
				newBean.setNumberType(ContactsUtil.subcalllogList.get(i).getNumberType());
				ContactsUtil.subcalllogList.get(i);
				if(listData.size() > 0){
					if(newBean.getDataBean().year.equals(dataBean.year) &&
							newBean.getDataBean().month.equals(dataBean.month) &&
							newBean.getDataBean().day.equals(dataBean.day)){
					}else{
						dataBean = newBean.getDataBean();
						newBean.setItemType(1);
						listData.add(newBean);
					}
				}else{
					dataBean = newBean.getDataBean();
					newBean.setItemType(1);
					listData.add(newBean);
				}
				ContactsUtil.subcalllogList.get(i).setItemType(0);
				listData.add(ContactsUtil.subcalllogList.get(i));
			}
		}
		if(listData != null && listData.size() > 0){
			isEmpty.setVisibility(View.GONE);
		}else{
			isEmpty.setVisibility(View.VISIBLE);
		}
		recycleviewAdpater.notifyDataSetChanged();
	};

	private void updataRecycleView() {
		// TODO Auto-generated method stub
		layoutManager = new LinearLayoutManager(getActivity());
		logRecycleView.setLayoutManager(layoutManager);
		logRecycleView.setHasFixedSize(true);
		DefaultItemAnimator anima = new DefaultItemAnimator();
		logRecycleView.setItemAnimator(anima);
		logRecycleView.setAdapter(recycleviewAdpater);
		if(listData != null && listData.size() > 0){
			isEmpty.setVisibility(View.GONE);
		}else{
			isEmpty.setVisibility(View.VISIBLE);
		}
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		isEmpty = (LinearLayout)view.findViewById(R.id.contactinfomation_calllog_isempty);
		logRecycleView = (RecyclerView)view.findViewById(R.id.contactinfomation_calllog_recylceview);
		dataAdjust = (LinearLayout)view.findViewById(R.id.contactinfomation_calllog_data_rootview_adjust);
	}
}

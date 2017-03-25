package com.example.fragment;

import java.util.ArrayList;

import com.example.adapter.ContactsInfomationMainRecycleAdapter;
import com.example.beautyphone.R;
import com.example.contacts.AdressInfomation;
import com.example.contacts.ContactInfomationBean;
import com.example.contacts.ContactInfomationNumberListBean;
import com.example.contacts.ContactInfomationOtherListBean;
import com.example.contacts.ContactsGroup;
import com.example.contacts.DataInfomation;
import com.example.contacts.EmaiInfomation;
import com.example.contacts.OhterInfomationBean;
import com.example.contacts.infomation.InfomationAddFriendData;
import com.example.contacts.infomation.InfomationBaseData;
import com.example.db.ContactsUtil;
import com.example.db.DataTypeUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactInfomationInfomationFragment extends Fragment{
	public RecyclerView mainRecycleyView = null;
	public LinearLayoutManager layoutManager = null;
	public ContactsInfomationMainRecycleAdapter mainAdapter = null;
	private ArrayList<InfomationBaseData> infomationData = null;
	private boolean recycleViewState = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.contactinfomation_infomation_layout, container, false);
		initView(view);
		initViewData();
		return view;
	}


	private void initViewData() {
		// TODO Auto-generated method stub
		infomationData = new ArrayList<InfomationBaseData>();
		mainAdapter = new ContactsInfomationMainRecycleAdapter(getActivity(), infomationData);
		layoutManager = new LinearLayoutManager(getActivity());
		mainRecycleyView.setLayoutManager(layoutManager);
		mainRecycleyView.setHasFixedSize(true);
		DefaultItemAnimator anima = new DefaultItemAnimator();
		mainRecycleyView.setItemAnimator(anima);
		mainRecycleyView.setNestedScrollingEnabled(false);
		mainRecycleyView.setAdapter(mainAdapter);
		ContactsUtil.getGroupList(getActivity(), handler);
	}

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 1){
				if(ContactsUtil.groupList != null && ContactsUtil.groupList.size() > 0){
					for (ContactsGroup c:ContactsUtil.groupList){
						Log.e("c", c.getGroup_id() + " + " + c.getGroup_name());
					}
				}
			}
		}
	};

	public void updataData(ContactInfomationBean contactInfomationBean){
		if(contactInfomationBean != null){
			if(infomationData == null){
				Log.e("c", "infomation为空");
				infomationData = new ArrayList<InfomationBaseData>();
			}else{
				infomationData.clear();
			}
			if(contactInfomationBean.getNumberList() != null && contactInfomationBean.getNumberList().size() > 0){
				ContactInfomationNumberListBean contactInfomationNumberListBean = new ContactInfomationNumberListBean();
				contactInfomationBean.setItemType(0);
				contactInfomationNumberListBean.setNumberList(contactInfomationBean.getNumberList());
				infomationData.add(contactInfomationNumberListBean);
			}

			InfomationAddFriendData addData = new InfomationAddFriendData();
			addData.setItemType(1);
			infomationData.add(addData);

			ContactInfomationOtherListBean contactInfomationOtherListBean = new ContactInfomationOtherListBean();
			ArrayList<OhterInfomationBean> otherInfomationList = new ArrayList<OhterInfomationBean>();
			contactInfomationOtherListBean.setItemType(2);

			String name = contactInfomationBean.getName();//名字
			if(name != null && !name.equals("")){
				OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
				ohterInfomationBean.setData(name);
				ohterInfomationBean.setTitle("姓名");
				ohterInfomationBean.setDescript("");
				otherInfomationList.add(ohterInfomationBean);
			}

			String company = contactInfomationBean.getCompany();//公司
			if(company != null && !company.equals("")){
				OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
				ohterInfomationBean.setData(company);
				ohterInfomationBean.setTitle("公司");
				ohterInfomationBean.setDescript("");
				otherInfomationList.add(ohterInfomationBean);
			}

			String job = contactInfomationBean.getJob();//职位
			if(job != null && !job.equals("")){
				OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
				ohterInfomationBean.setData(job);
				ohterInfomationBean.setTitle("职位");
				ohterInfomationBean.setDescript("");
				otherInfomationList.add(ohterInfomationBean);
			}

			String group = contactInfomationBean.getGroup();//群组
			if(group != null && !group.equals("")){
				OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
				ohterInfomationBean.setData(DataTypeUtil.getGroupName(group));
				ohterInfomationBean.setTitle("群组");
				ohterInfomationBean.setDescript("");
				otherInfomationList.add(ohterInfomationBean);
			}

			ArrayList<DataInfomation> dataList= contactInfomationBean.getDataList();//日期
			if(dataList != null && dataList.size() > 0){
				for (DataInfomation d: dataList){
					OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
					ohterInfomationBean.setData(d.getData());
					ohterInfomationBean.setTitle("日期");
					ohterInfomationBean.setDescript(DataTypeUtil.getDataDescript(d.getDataType()));
					otherInfomationList.add(ohterInfomationBean);
				}
			}

			ArrayList<EmaiInfomation> emailList= contactInfomationBean.getEmailList();//日期
			if(emailList != null && emailList.size() > 0){
				for (EmaiInfomation d: emailList){
					OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
					ohterInfomationBean.setData(d.getEmailAdress());
					ohterInfomationBean.setTitle("邮箱");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
					ohterInfomationBean.setDescript(DataTypeUtil.getEmailDescript(d.getEmailType()));
					otherInfomationList.add(ohterInfomationBean);
				}
			}

			ArrayList<AdressInfomation> adressList= contactInfomationBean.getAdressList();//地址
			if(adressList != null && adressList.size() > 0){
				for (AdressInfomation d: adressList){
					OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
					ohterInfomationBean.setData(d.getAdress());
					ohterInfomationBean.setTitle("地址");
					ohterInfomationBean.setDescript(DataTypeUtil.getAdressDescript(d.getAdressType()));
					otherInfomationList.add(ohterInfomationBean);
				}
			}

			String note = contactInfomationBean.getNote();//备注
			if(note != null && !note.equals("")){
				OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
				ohterInfomationBean.setData(note);
				ohterInfomationBean.setTitle("备注");
				ohterInfomationBean.setDescript("");
				otherInfomationList.add(ohterInfomationBean);
			}

			String nikeName = contactInfomationBean.getNikeName();//群组
			if(nikeName != null && !nikeName.equals("")){
				OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
				ohterInfomationBean.setData(nikeName);
				ohterInfomationBean.setTitle("昵称");
				ohterInfomationBean.setDescript("");
				otherInfomationList.add(ohterInfomationBean);
			}

			String webSite = contactInfomationBean.getWebSite();//群组
			if(webSite != null && !webSite.equals("")){
				OhterInfomationBean ohterInfomationBean = new OhterInfomationBean();
				ohterInfomationBean.setData(webSite);
				ohterInfomationBean.setTitle("网站");
				ohterInfomationBean.setDescript("");
				otherInfomationList.add(ohterInfomationBean);
			}
			contactInfomationOtherListBean.setOtherInfomationList(otherInfomationList);
			infomationData.add(contactInfomationOtherListBean);
			if(mainAdapter != null){
				mainAdapter.notifyDataSetChanged();
			}
		}
	}



	private void initView(View view) {
		// TODO Auto-generated method stubcontactinfomation_infomation_layout
		mainRecycleyView = (RecyclerView)view.findViewById(R.id.contactinfomation_mainrecylceview);
	}
}

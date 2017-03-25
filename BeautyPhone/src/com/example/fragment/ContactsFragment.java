package com.example.fragment;


import java.util.ArrayList;

import com.example.activity.ContactsInfomationActivity;
import com.example.adapter.ContactsListAdapter;
import com.example.adapter.ContactsListAdapter.onClickLister;
import com.example.beautyphone.R;
import com.example.beautyphone.StaticObject;
import com.example.contacts.ContactPeopleBean;
import com.example.db.ContactsUtil;
import com.example.db.ContactsUtil.contactsCallBack;
import com.example.myview.MySideBar;
import com.example.myview.MySideBar.sideBarPositionListen;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ContactsFragment extends Fragment implements OnClickListener{
	private String mySideBarChoseChar = "";
	private ArrayList<ContactPeopleBean> allContactsListData = null;
	private RecyclerView contactsRecycleView= null;
	private LinearLayoutManager layoutmanager = null;
	private MySideBar mySideBar = null;
	private LinearLayout addNewContactButton = null;
	private ContactsListAdapter contactsListAdapter = null;
	private ContactsListAdapter callListAdapter = null;
	private ArrayList<String> listData = null;
	private ContactListChangeBroadCase contactListListener = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.contactslist_fragment, container, false);
		initView(view);
		initViewDate();
		return view;
	}
	private void initViewDate() {
		// TODO Auto-generated method stub
		allContactsListData = new ArrayList<ContactPeopleBean>();
		layoutmanager = new LinearLayoutManager(getActivity());
		contactsRecycleView.setLayoutManager(layoutmanager);
		contactsRecycleView.setHasFixedSize(true);
		DefaultItemAnimator anima = new DefaultItemAnimator();
		contactsRecycleView.setItemAnimator(anima);
		contactsListAdapter = new ContactsListAdapter(getActivity(), allContactsListData);
		contactsRecycleView.setAdapter(contactsListAdapter);
		//	Toast.makeText(getActivity(), "经过", 0).show();
		beginToGetContacts();
	}
	private void beginToGetContacts() {
		try{
			ContactsUtil.getContacts(getActivity(), new contactsCallBack() {
				@Override
				public void allContactsCuror(ArrayList<ContactPeopleBean> contactsPeoeleListData) {
					// TODO Auto-generated method stub
					initData(contactsPeoeleListData);
				}
			});
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	public class ContactListChangeBroadCase extends ContentObserver{
		private Context context = null;
		private Handler handler = null;
		public ContactListChangeBroadCase(Context context ,Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.handler = handler;
		}
		@Override
		public void onChange(boolean selfChange, Uri uri) {
			// TODO Auto-generated method stub
			super.onChange(selfChange, uri);
			beginToGetContacts();
		}
	}

	private Handler contactChangeHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {

		};
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(contactListListener != null){
			getActivity().getContentResolver().unregisterContentObserver(contactListListener);
		}
	}


	private void initData(ArrayList<ContactPeopleBean> contactsPeoeleListData) {
		if(contactListListener == null){
			contactListListener = new ContactListChangeBroadCase(getActivity(), contactChangeHandler);
			getActivity().getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI,
					true, contactListListener);
		}
		ArrayList<ContactPeopleBean> bean = ContactsUtil.getSterredList(contactsPeoeleListData);
		if(allContactsListData == null){
			allContactsListData = new ArrayList<ContactPeopleBean>();
		}else{
			allContactsListData.clear();
		}
		//allContactsListData = new ArrayList<ContactPeopleBean>();
		ContactPeopleBean sterredBean = new ContactPeopleBean();	//
		ContactPeopleBean adjustBean = new ContactPeopleBean();
		sterredBean.setSterredList(bean);
		sterredBean.setItemType(0);
		adjustBean.setItemType(1);
		allContactsListData.add(sterredBean);		//把收藏的列表添加到第一个
		allContactsListData.add(adjustBean);	//多加一个无关的item作为调整item，这个对象无关系的
		allContactsListData.addAll(contactsPeoeleListData);
		allContactsListData = ContactsUtil.groupByPinyFirstChar(allContactsListData);
		//contactsListAdapter = new ContactsListAdapter(getActivity(), allContactsListData);
		boolean is = allContactsListData == this.allContactsListData? true:false;
		contactsListAdapter.setOnClickLister(new onClickLister() {

			@Override
			public void click(int position) {
				// TODO Auto-generated method stub
				Log.e("c", "联系人点击位置:" + position);
				Intent intent = new Intent(getActivity(), ContactsInfomationActivity.class);
				intent.putExtra("data1", allContactsListData.get(position).getContactId() + "");
				intent.putExtra("intentType", "contactMainItem");
				startActivity(intent);
			}
		});
		//layoutmanager = new LinearLayoutManager(getActivity());
		/*contactsRecycleView.setLayoutManager(layoutmanager);
		contactsRecycleView.setHasFixedSize(true);
		DefaultItemAnimator anima = new DefaultItemAnimator();
		contactsRecycleView.setItemAnimator(anima);*/
		//contactsRecycleView.setAdapter(contactsListAdapter);
		contactsListAdapter.notifyDataSetChanged();
		contactsRecycleView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				// TODO Auto-generated method stub
				super.onScrollStateChanged(recyclerView, newState);
				String choseC = allContactsListData.get(layoutmanager.findFirstVisibleItemPosition()).getNamePinYFirstChar();
				if(!mySideBarChoseChar.equals(choseC)){
					mySideBar.chosedChar(choseC);
					mySideBarChoseChar = choseC;
				}
			}
		});
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		contactsRecycleView = (RecyclerView)view.findViewById(R.id.contactsfragment_recylceview);
		mySideBar = (MySideBar)view.findViewById(R.id.contactslistfragment_mysidebar);
		addNewContactButton = (LinearLayout)view.findViewById(R.id.contactsfragment_add_newcontact);
		addNewContactButton.setOnClickListener(this);
		mySideBar.setOnSideBarPositionListener(new sideBarPositionListen() {
			@Override
			public void chosed(int position, String choseChar) {
				// TODO Auto-generated method stub
				Log.e("c", "position: " + position + " + choseChar: " + choseChar);
				if(StaticObject.pinyinIndexMap.get(choseChar) != null){
					MoveToPosition(layoutmanager, contactsRecycleView, (Integer) (StaticObject.pinyinIndexMap.get(choseChar)));
				}
			}
		});
	}

	/**
	 * RecyclerView 移动到当前位置，
	 *
	 * @param manager   设置RecyclerView对应的manager
	 * @param mRecyclerView  当前的RecyclerView
	 * @param n  要跳转的位置
	 */
	public void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
		int firstItem = manager.findFirstVisibleItemPosition();
		int lastItem = manager.findLastVisibleItemPosition();
		if (n <= firstItem) {
			mRecyclerView.scrollToPosition(n);
		} else if (n <= lastItem) {
			int top = mRecyclerView.getChildAt(n - firstItem).getTop();
			mRecyclerView.scrollBy(0, top);
		} else {
			mRecyclerView.scrollToPosition(n);
		}

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contactsfragment_add_newcontact:
			addNewContact();
			break;
		}
	}
	private void addNewContact() {
		// TODO Auto-generated method stub
		Intent addIntent = new Intent(Intent.ACTION_INSERT,Uri.withAppendedPath(Uri.parse("content://com.android.contacts"), "contacts"));
		addIntent.setType("vnd.android.cursor.dir/person");
		addIntent.setType("vnd.android.cursor.dir/contact");
		addIntent.setType("vnd.android.cursor.dir/raw_contact");
		addIntent.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, "");
		startActivity(addIntent);
	}

}

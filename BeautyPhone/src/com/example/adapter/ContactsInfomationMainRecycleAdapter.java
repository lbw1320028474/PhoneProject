package com.example.adapter;

import java.util.ArrayList;

import com.example.beautyphone.R;
import com.example.contacts.ContactInfomationNumberListBean;
import com.example.contacts.ContactInfomationOtherListBean;
import com.example.contacts.NumberInfomation;
import com.example.contacts.OhterInfomationBean;
import com.example.contacts.infomation.InfomationBaseData;
import com.example.contacts.infomation.InfomationPhoneData;
import com.example.db.ContactsUtil;
import com.example.util.UnitConverterUtil;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


public class ContactsInfomationMainRecycleAdapter extends RecyclerView.Adapter<ViewHolder> implements OnClickListener, OnLongClickListener{
	private LayoutInflater mInflater;
	private ArrayList<InfomationBaseData> allListData = null;
	private int position = 0;
	private LinearLayoutManager phoneRecycleViewLayoutManager = null;
	public Context context = null;
	private onClickLister oncliClickLister = null;
	private onLongClickLister longClickLister = null;


	public ContactsInfomationMainRecycleAdapter(Context context, ArrayList<InfomationBaseData> listData) {
		// TODO Auto-generated constructor stub
		this.allListData = listData;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setOnClickLister(onClickLister clickLister){
		this.oncliClickLister = clickLister;
	}

	public void setOnLongClickLister(onLongClickLister clickLister){
		this.longClickLister = clickLister;
	}

	class PhoneItemViewHolder extends ViewHolder{
		public RecyclerView phoneRecycleyView = null;
		public PhoneItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			phoneRecycleyView = (RecyclerView)itemView.findViewById(R.id.contactinfomation_phoneitem_recylceview);
		}
	}

	class InfomationViewHolder extends ViewHolder{
		public RecyclerView infomationRecycleyView = null;
		public TextView nameTitle = null;
		public LinearLayout rootView = null;
		public InfomationViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			infomationRecycleyView = (RecyclerView)itemView.findViewById(R.id.contactinfomation_infomation_recylceview);
			nameTitle = (TextView)itemView.findViewById(R.id.contactinfomation_infomation_action_title);
			rootView = (LinearLayout)itemView.findViewById(R.id.contactinfomation_infomation_rootView);
		}
	}

	class AddFriendItemViewHolder extends ViewHolder{
		public ImageView addFriendIcon = null;
		public TextView addFriendText = null;
		public TextView addFriend_describe = null;
		public TextView addFriend_addfriend = null;
		public AddFriendItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			addFriendIcon = (ImageView)itemView.findViewById(R.id.contactinfomation_addfriend_imageicon);
			addFriendText = (TextView)itemView.findViewById(R.id.contactinfomation_addfriend_text);
			addFriend_describe = (TextView)itemView.findViewById(R.id.contactinfomation_addfriend_describe);
			addFriend_addfriend = (TextView)itemView.findViewById(R.id.contactinfomation_addfriend_addfriend);
		}
	}



	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		int itemType = ((allListData.get(position))).getItemType();
		return itemType;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return allListData.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		if(arg0.getItemViewType() == 0){
			//((PhoneItemViewHolder)(arg0)).phoneRecycleyView.seta
			ArrayList<NumberInfomation> number = ((ContactInfomationNumberListBean)(allListData.get(arg1))).getNumberList();
			LayoutParams layoutParams = (LayoutParams) ((PhoneItemViewHolder)(arg0)).phoneRecycleyView.getLayoutParams();
			if(number!= null && number.size() > 0){
				Log.e("c", "∫≈¬Î ˝æ›: " + number.size() + " + " + number.get(0).getNumber());
				layoutParams.height = UnitConverterUtil.dip2px(context, 70 * number.size());
				((PhoneItemViewHolder)(arg0)).phoneRecycleyView.setLayoutParams(layoutParams);
			}else{
				layoutParams.height = 0;
				((PhoneItemViewHolder)(arg0)).phoneRecycleyView.setLayoutParams(layoutParams);
			}
			ContactsInfomationPhoneRecycleAdapter adapter = new ContactsInfomationPhoneRecycleAdapter(this.context, number);
			phoneRecycleViewLayoutManager = new LinearLayoutManager(this.context);
			((PhoneItemViewHolder)(arg0)).phoneRecycleyView.setLayoutManager(phoneRecycleViewLayoutManager);
			((PhoneItemViewHolder)(arg0)).phoneRecycleyView.setHasFixedSize(true);
			DefaultItemAnimator anima = new DefaultItemAnimator();
			//((PhoneItemViewHolder)(arg0)).phoneRecycleyView.setNestedScrollingEnabled(false);
			((PhoneItemViewHolder)(arg0)).phoneRecycleyView.setItemAnimator(anima);
			((PhoneItemViewHolder)(arg0)).phoneRecycleyView.setAdapter(adapter);
			Log.e("c", "  ≈‰∆˜…Ë÷√ÕÍ±œ");
		}else if(arg0.getItemViewType() == 1){

		}else if(arg0.getItemViewType() == 2){
			ContactInfomationOtherListBean otherData = ((ContactInfomationOtherListBean)(allListData.get(arg1)));
			LayoutParams layoutParams = (LayoutParams) ((InfomationViewHolder)(arg0)).infomationRecycleyView.getLayoutParams();
			ArrayList<OhterInfomationBean> otherInfomationList = otherData.getOtherInfomationList();
			if(otherInfomationList!= null && otherInfomationList.size() > 0){
				layoutParams.height = UnitConverterUtil.dip2px(context, 65 * otherInfomationList.size());
				((InfomationViewHolder)(arg0)).infomationRecycleyView.setLayoutParams(layoutParams);
			}else{
				layoutParams.height = 0;
				((InfomationViewHolder)(arg0)).infomationRecycleyView.setLayoutParams(layoutParams);
			}

			ContactsInfomationInfomationRecycleAdapter adapter = new ContactsInfomationInfomationRecycleAdapter(this.context, otherInfomationList);
			phoneRecycleViewLayoutManager = new LinearLayoutManager(this.context);
			((InfomationViewHolder)(arg0)).infomationRecycleyView.setLayoutManager(phoneRecycleViewLayoutManager);
			((InfomationViewHolder)(arg0)).infomationRecycleyView.setHasFixedSize(true);
			DefaultItemAnimator anima = new DefaultItemAnimator();
			//((InfomationViewHolder)(arg0)).infomationRecycleyView.setNestedScrollingEnabled(false);
			((InfomationViewHolder)(arg0)).infomationRecycleyView.setItemAnimator(anima);
			((InfomationViewHolder)(arg0)).infomationRecycleyView.setAdapter(adapter);
			((InfomationViewHolder)(arg0)).nameTitle.setText(ContactsUtil.infomationBean.getName() + " ºÚΩÈ");
			Log.e("c", "  ≈‰∆˜…Ë÷√ÕÍ±œ");
		}
		/*if(allListData.get(arg1) != null && !allListData.get(arg1).endsWith("")){
			((ItemViewHolder)(arg0)).phone_number.setText(allListData.get(arg1));
			((ItemViewHolder)(arg0)).item_phone_icon.setOnClickListener(this);
			((ItemViewHolder)(arg0)).item_sendmessage_icon.setOnClickListener(this);
			((ItemViewHolder)(arg0)).phone_number.setOnLongClickListener(this);
		}*/
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		/*View view = mInflater.inflate(R.layout.contacts_listview_item, arg0, false);  
		MyViewHolder holder= new MyViewHolder(view);*/
		if(arg1 == 0){
			View view = null;
			view = mInflater.inflate(R.layout.infomation_phonenumber_item, arg0, false);
			PhoneItemViewHolder contactsViewHolder = new PhoneItemViewHolder(view);
			return contactsViewHolder;
		}else if(arg1 == 1){
			View view = null;
			view = mInflater.inflate(R.layout.infomation_addfriend_item, arg0, false);
			AddFriendItemViewHolder addFriendViewHolder = new AddFriendItemViewHolder(view);
			return addFriendViewHolder;
		}else if(arg1 == 2){
			View view = null;
			view = mInflater.inflate(R.layout.infomation_infomation_item, arg0, false);
			InfomationViewHolder addFriendViewHolder = new InfomationViewHolder(view);
			return addFriendViewHolder;
		}
		return null;
	}

	public interface onClickLister{
		public void click(int position);
	}

	public interface onLongClickLister{
		public void longClick(int position);
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}

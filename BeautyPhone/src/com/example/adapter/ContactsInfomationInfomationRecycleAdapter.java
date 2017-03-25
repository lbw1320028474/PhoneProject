package com.example.adapter;

import java.util.ArrayList;

import com.example.beautyphone.R;
import com.example.contacts.OhterInfomationBean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ContactsInfomationInfomationRecycleAdapter extends RecyclerView.Adapter<ViewHolder> implements OnClickListener, OnLongClickListener{
	private LayoutInflater mInflater;
	private ArrayList<OhterInfomationBean> allListData = null;
	private int position = 0;
	public static Context context = null;
	private onClickLister oncliClickLister = null;
	private onLongClickLister longClickLister = null;


	public ContactsInfomationInfomationRecycleAdapter(Context context, ArrayList<OhterInfomationBean> listData) {
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

	class ItemViewHolder extends ViewHolder{
		public TextView info = null;
		public TextView descript = null;
		public ItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			info = (TextView)itemView.findViewById(R.id.contactinfomation_infomation_descript_title);
			descript = (TextView)itemView.findViewById(R.id.contactinfomation_infomation_descript_context);
		}
	}


	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return allListData.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		if(allListData.get(arg1) != null){
			Log.e("c", "设置了item: " + allListData.get(arg1));
			((ItemViewHolder)(arg0)).info.setText(allListData.get(arg1).getData());
			((ItemViewHolder)(arg0)).descript.setText(allListData.get(arg1).getTitle());
			if(!allListData.get(arg1).getDescript().equals("")){
				((ItemViewHolder)(arg0)).descript.append("(" + allListData.get(arg1).getDescript() + ")");
			}
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		/*View view = mInflater.inflate(R.layout.contacts_listview_item, arg0, false);  
		MyViewHolder holder= new MyViewHolder(view);*/
		View view = null;
		view = mInflater.inflate(R.layout.infomation_infomation_item_item, arg0, false);
		ItemViewHolder contactsViewHolder = new ItemViewHolder(view);
		return contactsViewHolder;
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
		switch (arg0.getId()) {
		case R.id.contactinfomation_phone_number:
			/*if(longClickLister != null){
				longClickLister.longClick((Integer) arg0.getTag());
			}*/
			Log.e("", "长按电话号码");
			break;
		default:
			break;
		}

		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.contactinfomation_phone_imageicon:
			/*if(oncliClickLister != null){
				oncliClickLister.click((Integer) arg0.getTag());
			}*/
			Log.e("", "打电话");
			break;
		case R.id.contactinfomation_sendmessage:
			/*if(oncliClickLister != null){
				oncliClickLister.click((Integer) arg0.getTag());
			}*/
			Log.e("", "发送消息");
			break;
		}

	}

}

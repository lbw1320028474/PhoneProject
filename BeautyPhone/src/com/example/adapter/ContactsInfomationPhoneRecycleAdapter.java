package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.ContactsInfomationActivity;
import com.example.beautyphone.R;
import com.example.contacts.CardLocationBean;
import com.example.contacts.NumberInfomation;
import com.example.db.DataTypeUtil;
import com.example.myDialog.CallTypeChoseDialog;
import com.example.util.NumberLocationUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ContactsInfomationPhoneRecycleAdapter extends RecyclerView.Adapter<ViewHolder> implements OnClickListener, OnLongClickListener{
	private LayoutInflater mInflater;
	private ArrayList<NumberInfomation> allListData = null;
	private int position = 0;
	public static Context context = null;
	private CallTypeChoseDialog callTypeChoseDialog = null;
	private onClickLister oncliClickLister = null;
	private onLongClickLister longClickLister = null;


	public ContactsInfomationPhoneRecycleAdapter(Context context, ArrayList<NumberInfomation> listData){
		// TODO Auto-generated constructor stub
		this.allListData = listData;
		this.context = context;
		callTypeChoseDialog = new CallTypeChoseDialog(context);
		mInflater = LayoutInflater.from(context);
	}

	public void setOnClickLister(onClickLister clickLister){
		this.oncliClickLister = clickLister;
	}

	public void setOnLongClickLister(onLongClickLister clickLister){
		this.longClickLister = clickLister;
	}

	class ItemViewHolder extends ViewHolder{
		public ImageView item_phone_icon = null;
		public TextView phone_number = null;
		public TextView phone_describe = null;
		public RelativeLayout rootView = null;
		public TextView item_sendmessage_icon = null;
		public ItemViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			item_phone_icon = (ImageView)itemView.findViewById(R.id.contactinfomation_phone_imageicon);
			phone_number = (TextView)itemView.findViewById(R.id.contactinfomation_phone_number);
			phone_describe = (TextView)itemView.findViewById(R.id.contactinfomation_phone_describe);
			item_sendmessage_icon = (TextView)itemView.findViewById(R.id.contactinfomation_sendmessage);
			rootView = (RelativeLayout)itemView.findViewById(R.id.contactinfomation_phone_rootView);
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
		//Log.e("c", "设置了item: " + allListData.get(arg1).toString());
		final int position = arg1;
		if(allListData.get(arg1) != null){
			((ItemViewHolder)(arg0)).rootView.setTag(arg1);
			((ItemViewHolder)(arg0)).item_sendmessage_icon.setTag(arg1);
			((ItemViewHolder)(arg0)).item_phone_icon.setColorFilter(ContactsInfomationActivity.styleColor);
			((ItemViewHolder)(arg0)).item_sendmessage_icon.setTextColor(ContactsInfomationActivity.styleColor);
			((ItemViewHolder)(arg0)).phone_number.setText(allListData.get(arg1).getNumber());
			if(allListData.get(arg1).getNumberDescript() != null && !allListData.get(arg1).getNumberDescript().equals(""))
			{
				((ItemViewHolder)(arg0)).phone_describe.setText(DataTypeUtil.
						getNumberDescript(allListData.get(arg1).getNumberDescript()));
			}
		}
	}



	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		/*View view = mInflater.inflate(R.layout.contacts_listview_item, arg0, false);  
		MyViewHolder holder= new MyViewHolder(view);*/
		View view = null;
		view = mInflater.inflate(R.layout.infomation_phonenumber_item_item, arg0, false);
		ItemViewHolder contactsViewHolder = new ItemViewHolder(view);
		contactsViewHolder.item_sendmessage_icon.setOnClickListener(this);
		contactsViewHolder.rootView.setOnClickListener(this);
		contactsViewHolder.item_sendmessage_icon.setOnClickListener(this);
		contactsViewHolder.rootView.setOnLongClickListener(this);
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
		case R.id.contactinfomation_phone_rootView:
			/*if(longClickLister != null){
				longClickLister.longClick((Integer) arg0.getTag());
			}*/
			ClipboardManager clip = (ClipboardManager)this.context.getSystemService(Context.CLIPBOARD_SERVICE);
			if(allListData.get((int) arg0.getTag()).getNumber() != null && !allListData.get((int) arg0.getTag()).getNumber().equals("")){
				clip.setText(allListData.get((int) arg0.getTag()).getNumber()); // 复制
				Toast.makeText(context, "号码已复制", 0).show();
			}else{
				Toast.makeText(context, "电话为空", 0).show();
			}
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
		case R.id.contactinfomation_phone_rootView:
			/*if(oncliClickLister != null){
				oncliClickLister.click((Integer) arg0.getTag());
			}*/
			callTypeChoseDialog.showDialog(allListData.get((int) arg0.getTag()).getNumber(), allListData.get((int) arg0.getTag()).getName(), "");
			Log.e("", "打电话");
			break;
		case R.id.contactinfomation_sendmessage:
			/*if(oncliClickLister != null){
				oncliClickLister.click((Integer) arg0.getTag());
			}*/
			String number = allListData.get((int) arg0.getTag()).getNumber();
			Uri smsToUri = Uri.parse("smsto:" + number);
			Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
			intent.putExtra("sms_body", "");
			context.startActivity(intent);
			Log.e("", "发送消息");
			break;
		}

	}

}
